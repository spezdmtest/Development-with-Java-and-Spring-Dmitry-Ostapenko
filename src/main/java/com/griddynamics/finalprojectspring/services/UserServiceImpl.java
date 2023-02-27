package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.config.UserIntegrationConfig;
import com.griddynamics.finalprojectspring.dto.UserDTO;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.mapper.UserMapper;
import com.griddynamics.finalprojectspring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;


@Service
@AllArgsConstructor
public class  UserServiceImpl implements UserService{

    private final UserMapper userMapper = UserMapper.USER_MAPPER;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserIntegrationConfig userIntegrationConfig;

    @Transactional
    public UserDTO createOrUpdate(@NotNull User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.fromUser(repository.save(user));
    }

    public UserDTO findById(Long id) {
        return userMapper.fromUser(repository.getById(id));
    }

    public List<UserDTO> findAll() {
        return userMapper.fromUserList(repository.findAll());
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
        var savedUser = repository.save(user);
    //    sendIntegrationNotify(savedUser);
    }

    @Override
    public boolean save(UserDTO userDTO) {
       User user = User.builder()
               .email(userDTO.getEmail())
               .password(passwordEncoder.encode(userDTO.getPassword()))
               .build();
       repository.save(user);
       return true;
    }

    private void sendIntegrationNotify(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());

        Message<UserDTO> message = MessageBuilder
                .withPayload(userDTO)
                .setHeader("Content-type", "application/json")
                .build();
        userIntegrationConfig.getUsersChannel().send(message);
    }

}
