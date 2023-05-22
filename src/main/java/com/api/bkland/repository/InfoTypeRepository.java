package com.api.bkland.repository;

import com.api.bkland.entity.InfoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoTypeRepository extends JpaRepository<InfoType, Integer> {
}
