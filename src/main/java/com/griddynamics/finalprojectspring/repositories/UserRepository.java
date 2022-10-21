package com.griddynamics.finalprojectspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.griddynamics.finalprojectspring.entities.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
       Optional<User> findUserByEmail(String email);
       User findFirstByEmail(String email);
}
