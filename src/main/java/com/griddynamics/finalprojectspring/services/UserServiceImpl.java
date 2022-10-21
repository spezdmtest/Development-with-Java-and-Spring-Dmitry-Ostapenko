package com.griddynamics.finalprojectspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.repositories.UserRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class  UserServiceImpl implements UserService{
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createOrUpdate(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existById(Long id) { return repository.existsById(id); }

    @Override
    public User findByName(String email) {
        return repository.findFirstByEmail(email);
    }
    @Override
    public void save(User user) {
        repository.save(user);
    }
}
