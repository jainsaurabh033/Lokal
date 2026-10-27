package com.lokal.lokal.Service;

import com.lokal.lokal.dto.SellerRegistrationRequest;
import com.lokal.lokal.entity.Store;
import com.lokal.lokal.entity.User;
import com.lokal.lokal.enumm.Role;
import com.lokal.lokal.enumm.StoreStatus;
import com.lokal.lokal.repository.StoreRepository;
import com.lokal.lokal.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

public class SellerRegistrationService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    public SellerRegistrationService(
            UserRepository userRepository,
            StoreRepository storeRepository,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Store registerSeller(SellerRegistrationRequest request){

        String normalizedEmail = request.getEmail().trim()
                .toLowerCase(java.util.Locale.ROOT);

        // 1. Reject an email that is already registered
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists");
        }

        // 2. Create the seller account.
        User seller = new User(request.getFullName().trim(), normalizedEmail, passwordEncoder.encode(request.getPassword()), Role.SELLER);

        // 3. Save the seller so the database generates their ID.
        User savedSeller = userRepository.save(seller);

        // 4. Create the seller's store.
        Store store = new Store(request.getFullName());
        store.setSeller(savedSeller);
        store.setStatus(StoreStatus.PENDING);

        // 5. Save the store and return it.
        return storeRepository.save(store);
    }
}
