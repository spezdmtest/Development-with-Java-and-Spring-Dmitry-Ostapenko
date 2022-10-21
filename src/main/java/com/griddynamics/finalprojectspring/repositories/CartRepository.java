package com.griddynamics.finalprojectspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.griddynamics.finalprojectspring.entities.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {}
