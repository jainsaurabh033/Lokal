package com.lokal.lokal.controller;

import com.lokal.lokal.Service.ProductService;
import com.lokal.lokal.dto.ProductRequest;
import com.lokal.lokal.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        if(page < 0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page cannot be negative"
            );
        }

        if(size < 1 || size > 100){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page size must be between 1 and 100"
            );
        }

        Sort sort;

        if(direction.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        else if(direction.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Direction must be asc or desc"
            );
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return productService.getAllProducts(pageable);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request){
        Product savedProduct = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> product = productService.getProductById(id);

        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id);
        }
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
