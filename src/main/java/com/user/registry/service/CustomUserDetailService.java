package com.user.registry.service;

import com.user.registry.pojo.User;
import com.user.registry.repository.UsuarioRepository;
import com.user.registry.security.JWTFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UsuarioRepository usuarioR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Fetching user from database for authentication");
        User dbuser = usuarioR.findByName(username);
        if (dbuser != null && dbuser.getIsactive()) {
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(dbuser.getName(), dbuser.getPassword(), new ArrayList<>());

            return user;
        }
        return null;
    }

}
