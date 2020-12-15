package com.user.registry.controller;


import com.user.registry.pojo.AuthenticationRequest;
import com.user.registry.pojo.AuthenticationResponse;
import com.user.registry.pojo.User;
import com.user.registry.repository.UsuarioRepository;
import com.user.registry.security.JWTService;
import com.user.registry.service.CustomUserDetailService;
import com.user.registry.service.IUsuario;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;


@RestController
@RequestMapping("userregistry")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioR;

    @Autowired
    JWTService jwtService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    IUsuario userv;

    @RequestMapping(value = "/registeruser", method = RequestMethod.POST, produces = {
            "application/json"}, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> insertarUsuario(@RequestBody User usuario) throws Exception {
        LOGGER.info("Initiating User persistence");
        org.springframework.security.core.userdetails.User user = null;
        usuario.setLast_login(new Date());
        usuario.setModified(new Date());
        usuario.setCreated(new Date());
        usuario.setIsactive(true);
        final String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(usuario.getName(), usuario.getPassword(), new ArrayList<>()));


        usuario.setToken(token);
        User u = userv.insertarUsuario(usuario);

        LOGGER.info("Finishing User persistence");
        return new ResponseEntity<Object>(u, HttpStatus.OK);

    }

    @PostMapping(value = "/login", produces = {
            "application/json"}, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) throws Exception {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        final String token = jwtService.generateToken(customUserDetailService.loadUserByUsername(request.getUsername()));
        final User user = usuarioR.findByName(request.getUsername());
        if (user != null) {
            user.setLast_login(new Date());
            user.setModified(new Date());
            user.setToken(token);
            userv.actualizarUsuario(user);

        }

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
        return new ResponseEntity<Object>(authenticationResponse, HttpStatus.OK);

    }

}
