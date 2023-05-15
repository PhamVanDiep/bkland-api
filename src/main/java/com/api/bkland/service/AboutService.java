package com.api.bkland.service;

import com.api.bkland.entity.About;
import com.api.bkland.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AboutService {
    @Autowired
    private AboutRepository repository;

    public About get() {
        return repository.findById(1).get();
    }

    @Transactional
    public About update(About about) {
        return repository.save(about);
    }
}
