package com.inventory.inventory.Products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.inventory.Products.entity.Product;
import com.inventory.inventory.Users.entity.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByUser(User user);

}
