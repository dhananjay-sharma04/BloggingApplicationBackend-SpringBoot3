package com.blogapp.services.impl;

import com.blogapp.config.mapperconverter.DtoConverter;
import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.MediaTypeNotSupported;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Usr;
import com.blogapp.repositories.UsrRepo;
import com.blogapp.services.UsrSvc;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class UsrSvcImpl implements UsrSvc {

    @Autowired
    private DtoConverter<UsrDto, Usr> dtoConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsrRepo usrRepo;
    private final Gson gson = new Gson();
    @Autowired
    private FileSvcImpl fileSvc;

    @Override
    public Page<Usr> getUsrList(int page, int pageSize) throws ResourceNotFound {
        log.info("Start execution of getUsrList method");

        log.info("Fetching list of user objects...");

        long noOfUserField = usrRepo.count();

        if (noOfUserField > 0) {
            log.info("{} User Field Objects Fetched successfully!", noOfUserField);
            Pageable pageable = PageRequest.of(page, pageSize);
            return usrRepo.findAll(pageable);
        } else {
            log.error("There is no users fields found in database. Please check.");
            throw new ResourceNotFound("No User found in database");
        }
    }

    @Override
    public Usr createUsr(String usrD, MultipartFile image) throws ResourceAlreadyExists, IOException, MediaTypeNotSupported {
        log.info("Started execution of createUsr method");

        UsrDto usrDto = gson.fromJson(usrD, UsrDto.class);

        List<Usr> userList = usrRepo.findAll()
                .stream()
                .filter(usr -> usr.getEmail().equalsIgnoreCase(usrDto.getEmail()))
                .toList();
        // Initializing model
        Usr usr;
        String imageName = null;
        if (userList.isEmpty()) {
            if (image != null) {
                try {
                    imageName = fileSvc.uploadImage(image);
                } catch (MediaTypeNotSupported mediaTypeNotSupported) {
                    throw new MediaTypeNotSupported(mediaTypeNotSupported.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            usr = dtoConverter.convert(usrDto, Usr.class);
            usr.setImageName(imageName);
            usr.setPassword(passwordEncoder.encode(usrDto.getPassword()));
            usrRepo.save(usr);
            log.info("User added Successfully");
        } else {
            throw new ResourceAlreadyExists("This user already exits! Please try with another email");
        }
        return usr;
    }

    @Override
    public Usr updateUsr(Long id, String usrD, MultipartFile image, String isDeleteImage) throws ResourceNotFound, ResourceAlreadyExists, IOException, MediaTypeNotSupported {
        log.info("Started execution of updateUser method");

        UsrDto usrDto = gson.fromJson(usrD, UsrDto.class);

        log.debug("Checking if User Field: {} with id: [{}] is exist in database", usrDto, id);
        Optional<Usr> userExist = usrRepo.findById(id);

        List<Usr> userList = usrRepo.findAll()
                .stream()
                .filter(usr -> usr.getEmail().equalsIgnoreCase(usrDto.getEmail()) &
                        !Objects.equals(usr.getId(), id))
                .toList();

        String imageName = null;
        if (!userList.isEmpty()) {
            log.error("user object is already exist. Please try with another email");
            throw new ResourceAlreadyExists("user object is already exist. Please try with another email");
        } else if (userExist.isPresent()) {

            if (image != null) {
                try {
                    imageName = fileSvc.uploadImage(image);

                } catch (MediaTypeNotSupported mediaTypeNotSupported) {
                    throw new MediaTypeNotSupported(mediaTypeNotSupported.getMessage());
                } catch (IOException e) {
                    throw new IOException(e.getMessage());
                }
            }

            String oldImageName = userExist.get().getImageName();

            log.info("User of Id: {} is exist, Updating server...", id);
            userExist.get().setId(id);
            userExist.get().setName(usrDto.getName());
            userExist.get().setEmail(usrDto.getEmail());
            userExist.get().setPassword(passwordEncoder.encode(usrDto.getPassword()));
            userExist.get().setAbout(usrDto.getAbout());

            if (image != null && isDeleteImage.equals("false")){
                userExist.get().setImageName(imageName);
            }
            if (image == null && isDeleteImage.equals("true")) {
                fileSvc.deleteImage(oldImageName);
                userExist.get().setImageName(imageName);
            }

            usrRepo.save(userExist.get());

            if (oldImageName != null && image != null){
                fileSvc.deleteImage(oldImageName);
            }
            log.info("User updated successfully with server object");
        } else {
            log.error("User object with id: {} is not exist in database. Please check.", id);
            throw new ResourceNotFound("User not found");
        }
        return userExist.get();
    }

    @Override
    public Map<String, Boolean> delUsr(Long id) throws ResourceNotFound, IOException {
        log.info("Started execution of deleteUser method");

        // checking user exists or not
        Optional<Usr> userExist = usrRepo.findById(id);

        Map<String, Boolean> response = new HashMap<>();

        if (userExist.isPresent()) {
            String imageName = userExist.get().getImageName();
            log.debug("user of Id: {} is exits, deleting server...", id);
            usrRepo.deleteById(id);
            if (imageName != null){
                fileSvc.deleteImage(imageName);
            }
            log.info("User of Id: {} is deleted successfully!", id);
            response.put("deleted", Boolean.TRUE);
            return response;
        } else {
            throw new ResourceNotFound("user not found");
        }
    }
}