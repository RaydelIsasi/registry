package com.user.registry.repository;


import com.user.registry.pojo.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;


public interface UsuarioRepository extends CrudRepository<User, UUID> {

    public List<User> findByEmail(String email);
    public User findByName(String name);

}
