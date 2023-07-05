package com.api.bkland.repository;

import com.api.bkland.entity.ProjectParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectParamRepository extends JpaRepository<ProjectParam, Long> {
    boolean existsByIdAndProjectId(Long id, String projectId);
    @Query(value = "select count(p.id) " +
            "from project_param pp inner join project p on pp.project_id = p.id " +
            "where pp.id = :id " +
            "and p.create_by = :userId", nativeQuery = true)
    long paramIsBelongToUser(Long id, String userId);
}
