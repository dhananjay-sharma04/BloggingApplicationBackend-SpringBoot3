package com.blogapp.securities;

import com.blogapp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthSvcImpl {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUsrDetailSvc customUsrDetailSvc;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse authenticate(JwtAuthRequest jwtAuthRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        this.authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = this.customUsrDetailSvc.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setUserDetails(userDetails);

        return jwtResponse;
    }
}