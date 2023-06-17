package com.blogapp.services.impl;

import com.blogapp.config.mapperconverter.DtoConverter;
import com.blogapp.dto.CmntDto;
import com.blogapp.dto.PostDto;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Catg;
import com.blogapp.models.Cmnt;
import com.blogapp.models.Post;
import com.blogapp.models.Usr;
import com.blogapp.repositories.CmntRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UsrRepo;
import com.blogapp.services.CmntSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CmntSvcImpl implements CmntSvc {
    @Autowired
    private DtoConverter<CmntDto, Cmnt> dtoConverter;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UsrRepo usrRepo;
    @Autowired
    private CmntRepo cmntRepo;

    @Override
    public List<Cmnt> getCmntByPost(Long postId) throws ResourceNotFound {
        log.info("Started execution of getCmntByPost method");

        log.debug("checking post with id : {} exist or not.", postId);
        Optional<Post> post = postRepo.findById(postId);

        if (post.isEmpty()){
            log.error("There is no post with id: {} found in database. Please check.", postId);
            throw new ResourceNotFound("No post found in database with id : " + postId);
        }
        List<Cmnt> cmntList = cmntRepo.findByPost(post.get());
        if (cmntList.isEmpty()) {
            log.error("There is no comments found in database with post id : {}.", postId);
            throw new ResourceNotFound("No Comments exist in database");
        } else {
            return cmntList;
        }
    }
    @Override
    public Cmnt createCmnt(CmntDto cmntDto, Long userId, Long postId) throws ResourceNotFound {
        log.info("Started execution of createCmnt method");

        log.debug("checking User with id : {} exist or not.", userId);
        Optional<Usr> user = usrRepo.findById(userId);

        log.debug("checking Post with id : {} exist or not.", postId);
        Optional<Post> post = postRepo.findById(postId);

        if (user.isEmpty()){
            log.error("There is no user with id: {} found in database. Please check.", userId);
            throw new ResourceNotFound("No user found in database with id : " + userId);
        } else if (post.isEmpty()){
            log.error("There is no post with id: {} found in database. Please check.", postId);
            throw new ResourceNotFound("No post found in database with id : " + postId);
        }
        // Initializing model
        Cmnt cmnt = dtoConverter.convert(cmntDto, Cmnt.class);
        cmnt.setUser(user.get());
        cmnt.setPost(post.get());
        cmntRepo.save(cmnt);
        log.info("Comment added Successfully");
        return cmnt;
    }

    @Override
    public Map<String, Boolean> delCmnt(Long id) throws ResourceNotFound {
        log.info("Started execution of delCmnt method");

        // checking comment exists or not
        Optional<Cmnt> cmntExist = cmntRepo.findById(id);

        Map<String, Boolean> response = new HashMap<>();

        if (cmntExist.isEmpty()){
            throw new ResourceNotFound("Comment not found with id : " + id);
        }
        log.debug("Comment of Id: {} is exits, deleting server...", id);
        cmntRepo.deleteById(id);
        log.info("Comment of Id: {} is deleted successfully!", id);
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}