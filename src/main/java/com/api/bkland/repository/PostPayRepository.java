package com.api.bkland.repository;

import com.api.bkland.entity.PostPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPayRepository extends JpaRepository<PostPay, Long> {
    List<PostPay> findByUserIdOrderByCreateAtDesc(String userId);
}
