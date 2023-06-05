package com.api.bkland.service;

import com.api.bkland.entity.PostReport;
import com.api.bkland.repository.PostReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostReportService {
    @Autowired
    private PostReportRepository repository;

    public void save(PostReport postReport) {
        repository.save(postReport);
    }
}
