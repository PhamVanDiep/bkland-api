package com.api.bkland.repository;

import com.api.bkland.entity.InfoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoTypeRepository extends JpaRepository<InfoType, Integer> {
    List<InfoType> findByIdGreaterThanEqual(Integer id);
}
