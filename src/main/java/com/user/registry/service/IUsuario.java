package com.user.registry.service;


import com.user.registry.pojo.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsuario {

	User insertarUsuario(User u) throws Exception;

	User actualizarUsuario(User u) throws Exception;

	String eliminarUsuario(UUID idusuario);

	Optional<User> buscarUsuario(UUID idusuario);




	List<User> listarUsuarios();

}
