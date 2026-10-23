package com.lokal.lokal.Service;

import com.lokal.lokal.dto.ProductRequest;
import com.lokal.lokal.entity.Product;
import com.lokal.lokal.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(ProductRequest request){
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
}
