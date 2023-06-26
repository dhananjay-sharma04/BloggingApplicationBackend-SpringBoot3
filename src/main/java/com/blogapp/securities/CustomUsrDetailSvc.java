package com.blogapp.securities;

import com.blogapp.exceptions.ResourceNotFound;
import com.blogapp.models.Usr;
import com.blogapp.repositories.UsrRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUsrDetailSvc implements UserDetailsService {

    @Autowired
    private UsrRepo usrRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //loading user from database by username (in our system email act as username)
        log.debug("fetching user with email : {}", email);
        Optional<Usr> user = usrRepo.findByEmail(email);
        if (user.isPresent()){
            return user.get();
        } else {
            log.error("No user found in database with email : {}", email);
            throw new UsernameNotFoundException("No user found in database with email : " + email);
        }
    }
}