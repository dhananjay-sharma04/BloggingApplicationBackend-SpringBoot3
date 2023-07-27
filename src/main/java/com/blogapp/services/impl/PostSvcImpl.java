package com.blogapp.services.impl;

import com.blogapp.config.mapperconverter.DtoConverter;
import com.blogapp.dto.PostDto;
import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Catg;
import com.blogapp.models.Post;
import com.blogapp.models.Usr;
import com.blogapp.repositories.CatgRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UsrRepo;
import com.blogapp.services.PostSvc;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class PostSvcImpl implements PostSvc {
    @Autowired
    private DtoConverter<PostDto, Post> dtoConverter;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UsrRepo usrRepo;
    @Autowired
    private CatgRepo catgRepo;
    private final Gson gson = new Gson();
    @Autowired
    private FileSvcImpl fileSvc;

    @Override
    public Post getPostById(Long id) throws ResourceNotFound {
        log.info("Start execution of getPostById method");

        Optional<Post> postExist = postRepo.findById(id);

        if (postExist.isPresent()) {
            log.info("Post object fetching with id: {} in database.", id);
            return postExist.get();
        } else {
            log.error("There is no post with id: {} found in database. Please check.", id);
            throw new ResourceNotFound("No Post found in database with id: " + id);
        }
    }

    @Override
    public List<Post> getPostList() throws ResourceNotFound {
        log.info("Start execution of getPostList method");

        List<Post> postList = postRepo.findAll();

        if (!postList.isEmpty()) {
            log.info("Fetching list of posts objects...");
            return postList;
        } else {
            log.error("There is no posts found in database. Please check.");
            throw new ResourceNotFound("No Posts exist in database");
        }
    }

    @Override
    public Page<Post> getPostList(int page, int pageSize) throws ResourceNotFound {
        log.info("Start execution of getPostList method");

        log.info("Fetching list of posts objects...");

        long noOfPostField = postRepo.count();

        if (noOfPostField > 0) {
            log.info("{} Posts Field Objects Fetched successfully!", noOfPostField);
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
            return postRepo.findAll(pageable);
        } else {
            log.error("There is no posts fields found in database. Please check.");
            throw new ResourceNotFound("No Post exist in database");
        }
    }

    @Override
    public List<Post> getPostByCategory(Long catId) throws ResourceNotFound {
        log.info("Started execution of getPostByCategory method");

        log.debug("checking Category with id : {} exist or not.", catId);
        Optional<Catg> category = catgRepo.findById(catId);

        if (category.isEmpty()) {
            log.error("There is no category with id: {} found in database. Please check.", catId);
            throw new ResourceNotFound("No category found in database with id : " + catId);
        }
        List<Post> postList = postRepo.findByCategory(category.get());
        if (postList.isEmpty()) {
            log.error("There is no posts found in database with category id : {}.", catId);
            throw new ResourceNotFound("No Post exist in database");
        } else {
            return postList;
        }
    }

    @Override
    public List<Post> getPostByUser(Long usrId) throws ResourceNotFound {
        log.info("Started execution of getPostByUser method");

        log.debug("checking User with id : {} exist or not.", usrId);
        Optional<Usr> user = usrRepo.findById(usrId);

        if (user.isEmpty()) {
            log.error("There is no user with id: {} found in database. Please check.", usrId);
            throw new ResourceNotFound("No user found in database with id : " + usrId);
        }
        List<Post> postList = postRepo.findByUser(user.get());
        if (postList.isEmpty()) {
            log.error("There is no posts found in database with user id : {}.", usrId);
            throw new ResourceNotFound("No Post exist in database");
        } else {
            return postList;
        }
    }

    @Override
    public List<Post> searchPost(String keyword) throws ResourceNotFound {
        log.info("Started execution of searchPost method");

        log.debug("fetching Post with title containing keyword : {} exist or not.", keyword);
        List<Post> postList = postRepo.findByTitleContaining(keyword);
        if (postList.isEmpty()) {
            log.error("Post object with title containing keyword : {} is not exist in database. Please check.", keyword);
            throw new ResourceNotFound("Post not found in database");
        } else {
            return postList;
        }
    }

    @Override
    public Post createPost(String postD, MultipartFile image, Long usrId, String catTitle) throws ResourceNotFound, IOException, MediaTypeNotSupported {
        log.info("Started execution of createPost method");

        PostDto postDto = gson.fromJson(postD, PostDto.class);

        log.debug("checking User with id : {} exist or not.", usrId);
        Optional<Usr> user = usrRepo.findById(usrId);

        log.debug("checking Category with id : {} exist or not.", catTitle);
        Catg category = catgRepo.findByCatgTitle(catTitle);

        if (user.isEmpty()) {
            log.error("There is no user with id: {} found in database. Please check.", usrId);
            throw new ResourceNotFound("No user found in database with id : " + usrId);
        } else if (category == null) {
            log.error("There is no category with id: {} found in database. Please check.", catTitle);
            throw new ResourceNotFound("No category found in database with title : " + catTitle);
        }
        String imageName = null;
        if (image != null) {
            try {
                imageName = fileSvc.uploadImage(image);
            } catch (MediaTypeNotSupported mediaTypeNotSupported) {
                throw new MediaTypeNotSupported(mediaTypeNotSupported.getMessage());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        // Initializing model
        Post post = dtoConverter.convert(postDto, Post.class);
        post.setImageName(imageName);
        post.setCategory(category);
        post.setUser(user.get());
        postRepo.save(post);
        log.info("Post added Successfully");
        return post;
    }

    @Override
    public Post updatePost(Long id, String post, MultipartFile image, String catTitle, String isDeleteImage) throws ResourceNotFound, MediaTypeNotSupported, IOException {
        log.info("Started execution of updatePost method");

        PostDto postDto = gson.fromJson(post, PostDto.class);

        log.debug("Checking if Post Field: {} with id: [{}] is exist in database", postDto, id);
        Optional<Post> postExist = postRepo.findById(id);
        String oldImageName;

        if (catTitle != null) {
            log.debug("checking Category with title : {} exist or not.", catTitle);
            Catg category = catgRepo.findByCatgTitle(catTitle);
            if (category != null && postExist.isPresent()) {
                postExist.get().setCategory(category);
            } else {
                throw new ResourceNotFound("category with title : " + catTitle + " not found in database");
            }
        }

        String imageName = null;
        if (image != null) {
            try {
                imageName = fileSvc.uploadImage(image);
            } catch (MediaTypeNotSupported mediaTypeNotSupported) {
                throw new MediaTypeNotSupported(mediaTypeNotSupported.getMessage());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }

        if (postExist.isPresent()) {
            log.info("Post of Id: {} is exist, Updating server...", id);
            oldImageName = postExist.get().getImageName();
            postExist.get().setId(id);
            postExist.get().setTitle(postDto.getTitle());
            postExist.get().setContent(postDto.getContent());
            if (image != null && isDeleteImage.equals("false")) {
                postExist.get().setImageName(imageName);
            }
            postRepo.save(postExist.get());
            if (image == null && isDeleteImage.equals("true")){
                fileSvc.deleteImage(oldImageName);
            } else if (oldImageName != null && image != null) {
                fileSvc.deleteImage(oldImageName);
            }
            log.info("Post updated successfully with server object");
        } else {
            log.error("Post object with id: {} is not exist in database. Please check.", id);
            if (image != null) {
                fileSvc.deleteImage(imageName);
            }
            throw new ResourceNotFound("Post not found in database");
        }
        return postExist.get();
    }

    @Override
    public Map<String, Boolean> delPost(Long id) throws ResourceNotFound, IOException {
        log.info("Started execution of delPost method");

        // checking post exists or not
        Optional<Post> postExist = postRepo.findById(id);

        Map<String, Boolean> response = new HashMap<>();
        String imageName;
        if (postExist.isPresent()) {
            imageName = postExist.get().getImageName();
            log.debug("Post of Id: {} is exits, deleting server...", id);
            postRepo.deleteById(id);
            if (imageName != null){
                fileSvc.deleteImage(imageName);
            }
            log.info("Post of Id: {} is deleted successfully!", id);
            response.put("deleted", Boolean.TRUE);
            return response;
        } else {
            throw new ResourceNotFound("Post not found with id : " + id);
        }
    }
}