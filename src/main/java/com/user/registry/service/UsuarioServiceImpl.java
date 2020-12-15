package com.user.registry.service;


import com.user.registry.exception.CustomException;
import com.user.registry.pojo.User;
import com.user.registry.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements IUsuario {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private UsuarioRepository usuarioR;

    @Override
    public User insertarUsuario(User u) throws Exception {

          LOGGER.info("Saving User into database");
        if (usuarioR.findByEmail(u.getEmail()).isEmpty()) {
            try {
                return usuarioR.save(u);


            } catch (ConstraintViolationException e) {
                throw new CustomException("Error al insertar el usuario");
            }

        } else {
            throw new CustomException("El correo ya registrado");
        }


    }

    @Override
    public User actualizarUsuario(User u) throws Exception {

        LOGGER.info("Updating  User");
        try {
            return usuarioR.save(u);

        } catch (DataIntegrityViolationException e) {
            throw new Exception("Usuario ya existe");
        }


    }

    @Override
    public String eliminarUsuario(UUID idusuario) {

        String r = "";

        try {
            usuarioR.deleteById(idusuario);


        } catch (Exception e) {

            r = "Falla al eliminar el usuario";
        }


        return r;

    }

    @Override
    public Optional<User> buscarUsuario(UUID idusuario) {

        Optional<User> u = usuarioR.findById(idusuario);

        return u;
    }


    @Override
    public List<User> listarUsuarios() {
        List<User> usuarios = (List<User>) usuarioR.findAll();
        return usuarios;
    }

}
