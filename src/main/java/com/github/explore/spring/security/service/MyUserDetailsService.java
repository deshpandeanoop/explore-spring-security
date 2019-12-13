package com.github.explore.spring.security.service;

import com.github.explore.spring.security.bean.MyUserDetails;
import com.github.explore.spring.security.bean.User;
import com.github.explore.spring.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOGGER.info("Fetching user details with username {}", username);
        User user = userRepository.findByUsername(username);
        if(null == user){
            throw new UsernameNotFoundException("User not found");
        }
       return new MyUserDetails(user);
    }

}