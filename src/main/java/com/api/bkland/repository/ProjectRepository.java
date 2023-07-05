package com.api.bkland.repository;

import com.api.bkland.constant.enumeric.EProjectType;
import com.api.bkland.entity.Project;
import com.api.bkland.entity.response.IProjectStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    @Modifying
    @Query("UPDATE Project SET enable = false WHERE id = :id")
    void deleteProject(String id);

    Optional<Project> findByIdAndEnable(String id, boolean enable);

    List<Project> findByCreateByAndEnable(String createBy, boolean enable, Sort sort);

    boolean existsByIdAndEnable(String id, boolean enable);

    List<Project> findByTypeAndEnable(EProjectType type, boolean enable, Pageable pageable);

    boolean existsByIdAndEnableAndCreateBy(String id, boolean enable, String createBy);

    @Query(value = "select count(p.id) as cnt, pr.full_name as label\n" +
            "from project p inner join provinces pr on pr.code = p.province_code\n" +
            "where year(p.create_at) = :year " +
            "group by pr.full_name;", nativeQuery = true)
    List<IProjectStatistic> getAllInYear(Integer year);

    @Query(value = "select count(pv.id) as cnt, pr.full_name as label\n" +
            "from project_view pv inner join project p on pv.project_id = p.id\n" +
            "inner join provinces pr on pr.code = p.province_code\n" +
            "where year(p.create_at) = :year " +
            "group by pr.full_name;", nativeQuery = true)
    List<IProjectStatistic> getByViewInYear(Integer year);

    @Query(value = "select count(pv.id) as cnt, pr.full_name as label\n" +
            "from project_interested pv inner join project p on pv.project_id = p.id\n" +
            "inner join provinces pr on pr.code = p.province_code\n" +
            "where year(p.create_at) = :year " +
            "group by pr.full_name;", nativeQuery = true)
    List<IProjectStatistic> getByInterestedInYear(Integer year);

    @Query(value = "select count(pv.id) as cnt, pr.full_name as label\n" +
            "from post_comment pv inner join project p on pv.post_id = p.id\n" +
            "inner join provinces pr on pr.code = p.province_code\n" +
            "where year(p.create_at) = :year " +
            "group by pr.full_name;", nativeQuery = true)
    List<IProjectStatistic> getByCommentInYear(Integer year);

    List<Project> findByEnable(boolean enable, Sort sort);
}
