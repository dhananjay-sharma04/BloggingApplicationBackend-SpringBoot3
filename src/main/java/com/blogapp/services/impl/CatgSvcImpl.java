package com.blogapp.services.impl;

import com.blogapp.config.mapperconverter.DtoConverter;
import com.blogapp.dto.CatgDto;
import com.blogapp.dto.UsrDto;
import com.blogapp.exceptions.ResourceAlreadyExists;
import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Catg;
import com.blogapp.models.Usr;
import com.blogapp.repositories.CatgRepo;
import com.blogapp.repositories.UsrRepo;
import com.blogapp.services.CatgSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CatgSvcImpl implements CatgSvc {
    @Autowired
    private DtoConverter<CatgDto, Catg> dtoConverter;
    @Autowired
    private CatgRepo catgRepo;
    @Override
    public List<Catg> getCatgList() throws ResourceNotFound {
        log.info("Start execution of getCatgList method");

        List<Catg> catgList = catgRepo.findAll();

        if (!catgList.isEmpty()) {
            log.info("Fetching list of categories objects...");
            return catgList;
        } else {
            log.error("There is no categories fields found in database. Please check.");
            throw new ResourceNotFound("No Categories found in database");
        }
    }

    @Override
    public Catg getCatgById(Long id) throws ResourceNotFound {
        log.info("Start execution of getCatgId method");

        Optional<Catg> catgExist = catgRepo.findById(id);

        if (catgExist.isPresent()) {
            log.info("Category object fetching with id: {} in database.", id);
            return catgExist.get();
        } else {
            log.error("There is no categories fields with id: {} found in database. Please check.", id);
            throw new ResourceNotFound("No Category found in database with id: " + id);
        }
    }

    @Override
    public Catg createCatg(CatgDto catgDto) throws ResourceAlreadyExists {
        log.info("Started execution of createCatg method");

        List<Catg> catgList = catgRepo.findAll()
                .stream()
                .filter(catg -> catg.getCatgTitle().equalsIgnoreCase(catgDto.getCatgTitle()))
                .toList();
        // Initializing model
        Catg catg;
        if (catgList.isEmpty()) {
            catg = dtoConverter.convert(catgDto, Catg.class);
            catgRepo.save(catg);
            log.info("Category added Successfully");
        } else {
            throw new ResourceAlreadyExists("This category already exits! with same title");
        }
        return catg;
    }

    @Override
    public Catg updateCatg(Long id, CatgDto catgDto) throws ResourceNotFound, ResourceAlreadyExists {
        log.info("Started execution of updateCatg method");

        log.debug("Checking if Category Field: {} with id: [{}] is exist in database", catgDto, id);
        Optional<Catg> catgExist = catgRepo.findById(id);

        List<Catg> catgList = catgRepo.findAll()
                .stream()
                .filter(catg -> catg.getCatgTitle().equalsIgnoreCase(catgDto.getCatgTitle()) &
                        !Objects.equals(catg.getId(), id))
                .toList();

        if (!catgList.isEmpty()) {
            log.error("This category already exits! with same title.");
            throw new ResourceAlreadyExists("This category already exits! with same title.");
        } else if (catgExist.isPresent()) {
            log.info("Category of Id: {} is exist, Updating server...", id);
            catgExist.get().setId(id);
            catgExist.get().setCatgTitle(catgDto.getCatgTitle());
            catgExist.get().setCatgDesc(catgDto.getCatgDesc());
            catgRepo.save(catgExist.get());
            log.info("Category updated successfully with server object");
        } else {
            log.error("Category object with id: {} is not exist in database. Please check.", id);
            throw new ResourceNotFound("Category not found");
        }
        return catgExist.get();
    }

    @Override
    public Map<String, Boolean> delCatg(Long id) throws ResourceNotFound {
        log.info("Started execution of delCatg method");

        // checking category exists or not
        Optional<Catg> catgExist = catgRepo.findById(id);

        Map<String, Boolean> response = new HashMap<>();

        if (catgExist.isEmpty()){
            throw new ResourceNotFound("Category not found with id : " + id);
        }
        log.debug("category of Id: {} is exits, deleting server...", id);
        catgRepo.deleteById(id);
        log.info("category of Id: {} is deleted successfully!", id);
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}