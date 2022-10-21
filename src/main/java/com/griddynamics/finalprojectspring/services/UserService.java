package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.entities.User;
import java.util.List;
import java.util.Optional;


public interface UserService {
    User createOrUpdate(User user);
    User findByName(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    void save(User user);
    void deleteById(Long id);
    boolean existById(Long id);
}
