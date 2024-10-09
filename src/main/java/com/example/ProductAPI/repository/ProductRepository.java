package com.example.ProductAPI.repository;

import com.example.ProductAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("select u from Product u where u.owner_id = ?1")
    List<Product> findByUserId(Integer userId);

}
