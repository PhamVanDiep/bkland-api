package com.api.bkland.repository;

import com.api.bkland.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    long countByPostId(String postId);
}
