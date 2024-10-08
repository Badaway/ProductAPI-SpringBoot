package com.example.ProductAPI.controller;

import com.example.ProductAPI.model.Product;
import com.example.ProductAPI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        product.setCreated_at(LocalDate.now().toString());
        return productService.newProduct(product);

    }


}
