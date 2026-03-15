package com.darshan.wallet.service;

import com.darshan.wallet.dto.RegisterRequest;
import com.darshan.wallet.entity.User;
import com.darshan.wallet.entity.Wallet;
import com.darshan.wallet.repository.UserRepository;
import com.darshan.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {
        //create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .status("ACTIVE")
                .kycVerified(false)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        //create wallet automatically
        Wallet wallet = Wallet.builder()
                .user(savedUser)
                .balance(BigDecimal.ZERO)
                .createdAt((LocalDateTime.now()))
                .build();

        walletRepository.save(wallet);

    }
    
}
