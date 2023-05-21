package com.api.bkland.service;

import com.api.bkland.entity.SpecialAccountPay;
import com.api.bkland.repository.SpecialAccountPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialAccountPayService {
    @Autowired
    private SpecialAccountPayRepository repository;

    public List<SpecialAccountPay> getSpecialAccountPaysByUserId(String userId) {
        return repository.findByUserIdOrderByCreateAtDesc(userId);
    }

    public List<SpecialAccountPay> findAllSpecialAccountPays() {
        List<SpecialAccountPay> specialAccountPays =
                repository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));
        return specialAccountPays;
    }
}
