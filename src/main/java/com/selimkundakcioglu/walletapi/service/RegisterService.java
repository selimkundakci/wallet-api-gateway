package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.model.entity.WalletUser;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.request.RegisterRequest;
import com.selimkundakcioglu.walletapi.repository.WalletUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService implements UserDetailsService {

    private final WalletUserRepository walletUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public WalletUser register(RegisterRequest registerRequest) {
        checkCustomerAlreadyExists(registerRequest.getEmail());
        WalletUser walletUser = createWalletUser(registerRequest);
        return walletUserRepository.save(walletUser);
    }

    private void checkCustomerAlreadyExists(String email) {
        Optional<WalletUser> registeredUser = walletUserRepository.findByEmailAndStatus(email, Status.ACTIVE);
        if (registeredUser.isPresent()) {
            throw new BusinessException("User already exists with this email: " + email);
        }
    }

    private WalletUser createWalletUser(RegisterRequest registerRequest) {
        return WalletUser.builder()
                .email(registerRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .status(Status.ACTIVE)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WalletUser walletUser = walletUserRepository.findByEmailAndStatus(username, Status.ACTIVE)
                .orElseThrow(() -> new BusinessException("User not found with given email: " + username));

        return User.builder()
                .username(walletUser.getId().toString())
                .password(walletUser.getPassword())
                .authorities("authority")
                .disabled(walletUser.getStatus() == Status.PASSIVE)
                .build();
    }
}
