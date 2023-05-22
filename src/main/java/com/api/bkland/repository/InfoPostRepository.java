package com.api.bkland.repository;

import com.api.bkland.entity.InfoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoPostRepository extends JpaRepository<InfoPost, Long> {
}
