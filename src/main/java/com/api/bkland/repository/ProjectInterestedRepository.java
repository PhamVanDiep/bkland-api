package com.api.bkland.repository;

import com.api.bkland.entity.Interested;
import com.api.bkland.entity.ProjectInterested;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectInterestedRepository extends JpaRepository<ProjectInterested, Long> {
    boolean existsByDeviceInfoAndProjectId(String deviceInfo, String projectId);
    boolean existsByUserIdAndProjectId(String userId, String projectId);
    Optional<ProjectInterested> findByDeviceInfoAndProjectId(String deviceInfo, String projectId);
    Optional<ProjectInterested> findByUserIdAndProjectId(String userId, String projectId);
    List<ProjectInterested> findByUserId(String userId, Sort sort);
}
