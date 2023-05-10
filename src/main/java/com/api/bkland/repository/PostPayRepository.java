package com.api.bkland.repository;

import com.api.bkland.entity.PostPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPayRepository extends JpaRepository<PostPay, Long> {
}
