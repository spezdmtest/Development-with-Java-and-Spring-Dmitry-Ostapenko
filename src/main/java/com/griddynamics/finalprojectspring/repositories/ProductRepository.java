package com.griddynamics.finalprojectspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.griddynamics.finalprojectspring.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {}

