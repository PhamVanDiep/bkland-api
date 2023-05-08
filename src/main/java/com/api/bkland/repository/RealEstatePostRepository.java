package com.api.bkland.repository;

import com.api.bkland.entity.RealEstatePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstatePostRepository extends JpaRepository<RealEstatePost, String> {
}
