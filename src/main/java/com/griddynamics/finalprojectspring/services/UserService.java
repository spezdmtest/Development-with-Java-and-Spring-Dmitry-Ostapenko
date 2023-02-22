package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.dto.UserDTO;
import com.griddynamics.finalprojectspring.entities.User;
import java.util.List;
import java.util.Optional;


public interface UserService {
    UserDTO createOrUpdate(User user);
    User findByName(String email);
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    void save(User user);
    boolean save(UserDTO userDTO);
    void deleteById(Long id);
    boolean existById(Long id);
}
