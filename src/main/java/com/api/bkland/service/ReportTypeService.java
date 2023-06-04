package com.api.bkland.service;

import com.api.bkland.entity.ReportType;
import com.api.bkland.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReportTypeService {
    @Autowired
    private ReportTypeRepository repository;

    @Transactional
    public ReportType save(ReportType reportType) {
        return repository.save(reportType);
    }

    public List<ReportType> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void deletePostReportTypeByReportTypeId(Integer reportTypeId) {
        repository.deletePostReportTypeByReportTypeId(reportTypeId);
    }

    @Transactional
    public void deletePostReportType(Integer id) {
        repository.deleteById(id);
    }
}
