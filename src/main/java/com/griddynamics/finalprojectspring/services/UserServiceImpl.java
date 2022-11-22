package com.griddynamics.finalprojectspring.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.repositories.UserRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class  UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public boolean existById(Long id) { return repository.existsById(id); }

    @Override
    @Transactional
    public User findByName(String email) {
        return repository.findFirstByEmail(email);
    }

    @Override
    @Transactional
    public void save(User user) {
        repository.save(user);
    }
}
