package com.griddynamics.finalprojectspring.repositories;

import com.griddynamics.finalprojectspring.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
      void checkFindUserByEmail() {
        // have
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");

        entityManager.persist(user);

        //execute
        User actualUser = userRepository.findFirstByEmail("test@test.com");

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(2, actualUser.getId());
        Assertions.assertEquals("test@test.com",actualUser.getEmail());
        Assertions.assertEquals("password",actualUser.getPassword());
    }
}

