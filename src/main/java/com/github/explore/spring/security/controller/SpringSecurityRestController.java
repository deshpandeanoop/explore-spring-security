package com.github.explore.spring.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityRestController.class);

    @GetMapping("/")
    public String home(){
        LOGGER.info("home page request received");
        return constructHeader("home");
    }

    @GetMapping("/user")
    public String user(){
        LOGGER.info("user page request received");
        return constructHeader("user");
    }

    @GetMapping("/admin")
    public String admin(){
        LOGGER.info("admin page request received");
        return constructHeader("admin");
    }

    private String constructHeader(String title){
        return new StringBuilder()
                .append("<h1 align='center'> Welcome to ")
                .append(title)
                .append(" page <h1>").toString();
    }
}