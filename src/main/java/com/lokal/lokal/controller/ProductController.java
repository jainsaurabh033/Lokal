package com.lokal.lokal.controller;

import com.lokal.lokal.Service.ProductService;
import com.lokal.lokal.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
