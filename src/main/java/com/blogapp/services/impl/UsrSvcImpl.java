package com.blogapp.services.impl;

import com.blogapp.config.mapperconverter.DtoConverter;
import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Usr;
import com.blogapp.repositories.UsrRepo;
import com.blogapp.services.UsrSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsrSvcImpl implements UsrSvc {

    @Autowired
    private DtoConverter<UsrDto, Usr> dtoConverter;

    @Autowired
    private UsrRepo usrRepo;

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
    public Usr createUsr(UsrDto usrDto) throws ResourceAlreadyExists {
        log.info("Started execution of createUsr method");

        List<Usr> userList = usrRepo.findAll()
                .stream()
                .filter(usr -> usr.getEmail().equalsIgnoreCase(usrDto.getEmail()))
                .toList();
        // Initializing model
        Usr usr;
        if (userList.isEmpty()) {
            usr = dtoConverter.convert(usrDto, Usr.class);
            usrRepo.save(usr);
            log.info("User added Successfully");
        } else {
            throw new ResourceAlreadyExists("This user already exits! Please try with another email");
        }
        return usr;
    }

    @Override
    public Usr updateUsr(Long id, UsrDto usrDto) throws ResourceNotFound, ResourceAlreadyExists {
        log.info("Started execution of updateUser method");

        log.debug("Checking if User Field: {} with id: [{}] is exist in database", usrDto, id);
        Optional<Usr> userExist = usrRepo.findById(id);

        List<Usr> userList = usrRepo.findAll()
                .stream()
                .filter(usr -> usr.getEmail().equalsIgnoreCase(usrDto.getEmail()) &
                        !Objects.equals(usr.getId(), id))
                .toList();

        if (!userList.isEmpty()) {
            log.error("user object is already exist. Please try with another email");
            throw new ResourceAlreadyExists("user object is already exist. Please try with another email");
        } else if (userExist.isPresent()) {
            log.info("User of Id: {} is exist, Updating server...", id);
            userExist.get().setId(id);
            userExist.get().setName(usrDto.getName());
            userExist.get().setEmail(usrDto.getEmail());
            userExist.get().setPassword(usrDto.getPassword());
            userExist.get().setAbout(usrDto.getAbout());
            usrRepo.save(userExist.get());
            log.info("User updated successfully with server object");
        } else {
            log.error("User object with id: {} is not exist in database. Please check.", id);
            throw new ResourceNotFound("User not found");
        }
        return userExist.get();
    }

    @Override
    public Map<String, Boolean> delUsr(Long id) throws ResourceNotFound {
        log.info("Started execution of deleteUser method");

        // checking user exists or not
        Optional<Usr> userExist = usrRepo.findById(id);

        Map<String, Boolean> response = new HashMap<>();

        if (userExist.isEmpty()){
            throw new ResourceNotFound("user not found");
        }
        log.debug("user of Id: {} is exits, deleting server...", id);
        usrRepo.deleteById(id);
        log.info("User of Id: {} is deleted successfully!", id);
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}