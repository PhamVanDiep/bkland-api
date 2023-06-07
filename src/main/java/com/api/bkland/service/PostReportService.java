package com.api.bkland.service;

import com.api.bkland.entity.PostReport;
import com.api.bkland.repository.PostReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostReportService {
    @Autowired
    private PostReportRepository repository;

    @Transactional
    public void save(PostReport postReport) {
        repository.save(postReport);
    }
    public Object getAllStatistic() {
        return repository.getListReportData();
    }

    public List<PostReport> findByPostId(String postId) {
        return repository.findByPostIdOrderByCreateAtDesc(postId);
    }
}
