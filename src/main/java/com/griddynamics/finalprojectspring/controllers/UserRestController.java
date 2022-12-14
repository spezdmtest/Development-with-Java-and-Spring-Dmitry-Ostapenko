package com.griddynamics.finalprojectspring.controllers;

import com.griddynamics.finalprojectspring.controllers.Exceptions.ValidateExistUser;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "User API", description = "Testing")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController {

    private UserService service;

    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findById(@PathVariable("id") Long id) throws NotFoundException {
        return service.findById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) throws ValidateExistUser {
        if (!service.existById(user.getId())) {
            service.createOrUpdate(user);
            return user;
        }
        throw new ValidateExistUser();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user) throws NotFoundException {
        if (service.existById(user.getId())) {
            service.createOrUpdate(user);
            return user;
        }
        throw new NotFoundException();
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteById(@PathVariable("id") Long id) throws NotFoundException {
        service.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> ExistExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>("Exist user", HttpStatus.CONFLICT);
    }
}
