package com.blogapp.config.security;

import com.blogapp.securities.CustomUsrDetailSvc;
import com.blogapp.securities.JwtAuthenticationEntryPoint;
import com.blogapp.securities.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration {
    @Autowired
    private CustomUsrDetailSvc customUsrDetailSvc;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public static final String[] PUBLIC_URL = {
            "/**",
//            "swagger-ui/**",
//            "/api",
//            "swagger-resource/**",
//            "webjars/**",
//            "swagger-ui.html"
    };
    public static final String[] ADMIN_URL = {
            "categories/add",
            "category/update",
            "category/delete",
            "categories/get",
            "users/**",
    };
    public static final String[] SHARED_URL = {
            "comments/**",
            "users/update/**",
            "categories/get",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorization) -> authorization
//                        .requestMatchers(ADMIN_URL).hasRole("ADMIN")
//                        .requestMatchers(SHARED_URL).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(PUBLIC_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}