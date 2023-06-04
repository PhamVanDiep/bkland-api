package com.api.bkland.repository;

import com.api.bkland.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Integer> {
    @Modifying
    @Query(value = "delete from post_report_type where report_type_id = :reportTypeId ;", nativeQuery = true)
    void deletePostReportTypeByReportTypeId(@Param("reportTypeId") Integer reportTypeId);
}
