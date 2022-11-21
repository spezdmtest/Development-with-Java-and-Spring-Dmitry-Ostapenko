package com.griddynamics.finalprojectspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.griddynamics.finalprojectspring.repositories.UserRepository;
import java.util.stream.Collectors;


@Service
public class UserAuthService implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    private void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findUserByEmail(email)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles().
                                stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())
                )).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
