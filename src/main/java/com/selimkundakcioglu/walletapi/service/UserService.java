package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.exception.BusinessException;
import com.selimkundakcioglu.walletapi.model.entity.WalletUser;
import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.repository.WalletUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WalletUserRepository walletUserRepository;

    public WalletUser getWalletUserById(Long id) {
        return walletUserRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BusinessException("user not found"));
    }
}
