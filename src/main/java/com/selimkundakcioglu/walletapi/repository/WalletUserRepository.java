package com.selimkundakcioglu.walletapi.repository;

import com.selimkundakcioglu.walletapi.model.entity.WalletUser;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletUserRepository extends JpaRepository<WalletUser, Long> {
    Optional<WalletUser> findByEmailAndStatus(String email, Status active);
    Optional<WalletUser> findByIdAndStatus(Long id, Status active);
}
