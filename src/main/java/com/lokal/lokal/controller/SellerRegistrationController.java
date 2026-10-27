package com.lokal.lokal.controller;

import com.lokal.lokal.Service.SellerRegistrationService;
import com.lokal.lokal.dto.SellerRegistrationRequest;
import com.lokal.lokal.entity.Store;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SellerRegistrationController {
    private final SellerRegistrationService sellerRegistrationService;

    public SellerRegistrationController(SellerRegistrationService sellerRegistrationService){
        this.sellerRegistrationService = sellerRegistrationService;
    }

    @PostMapping("/register/seller")
    public ResponseEntity<SellerRegistrationResponse> registerSeller(@Valid @RequestBody SellerRegistrationRequest request){
        Store store = sellerRegistrationService.registerSeller(request);

        SellerRegistrationResponse response = new SellerRegistrationResponse(store.getSeller().getId(), store.getName(), store.getStatus().name());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public record SellerRegistrationResponse(Long sellerId, String storeName, String status){}
}

