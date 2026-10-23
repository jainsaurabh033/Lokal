package com.lokal.lokal.controller;

import com.lokal.lokal.Service.ProductService;
import com.lokal.lokal.dto.ProductRequest;
import com.lokal.lokal.entity.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request){
        Product savedProduct = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}
