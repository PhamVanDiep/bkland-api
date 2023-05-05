package com.api.bkland.service;

import com.api.bkland.entity.SpecialAccount;
import com.api.bkland.repository.SpecialAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecialAccountService {
    @Autowired
    private SpecialAccountRepository repository;

    @Transactional
    public SpecialAccount addSpecialAccount(SpecialAccount specialAccount) {
        return repository.save(specialAccount);
    }
}
