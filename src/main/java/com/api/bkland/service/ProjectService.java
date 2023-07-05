package com.api.bkland.service;

import com.api.bkland.constant.enumeric.EProjectType;
import com.api.bkland.entity.Interested;
import com.api.bkland.entity.Project;
import com.api.bkland.entity.ProjectInterested;
import com.api.bkland.entity.ProjectView;
import com.api.bkland.entity.response.IProjectStatistic;
import com.api.bkland.payload.response.chart.ChartOption;
import com.api.bkland.repository.ProjectInterestedRepository;
import com.api.bkland.repository.ProjectParamRepository;
import com.api.bkland.repository.ProjectRepository;
import com.api.bkland.repository.ProjectViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;

    @Autowired
    private ProjectParamRepository projectParamRepository;

    @Autowired
    private ProjectInterestedRepository projectInterestedRepository;

    @Autowired
    private ProjectViewRepository projectViewRepository;

    @Transactional
    public String save(Project project) {
        return repository.save(project).getId();
    }

    @Transactional
    public void delete(String id) {
        repository.deleteProject(id);
    }

    public Project findById(String id, boolean increaseView) {
        if (increaseView) {
            ProjectView projectView = new ProjectView();
            projectView.setId(0L);
            projectView.setProjectId(id);
            projectView.setCreateAt(Instant.now());
            projectViewRepository.save(projectView);
        }
        Optional<Project> projectOptional = repository.findByIdAndEnable(id, true);
        return projectOptional.orElse(null);
    }

    public List<Project> findByUserId(String userId) {
        return repository.findByCreateByAndEnable(userId, true, Sort.by(Sort.Direction.DESC, "createAt"));
    }

    public List<Project> findAll() {
        return repository.findByEnable(true, Sort.by(Sort.Direction.DESC, "createAt"));
    }

    public boolean existsByIdAndEnable(String id) {
        return repository.existsByIdAndEnable(id, true);
    }

    public boolean paramExistsByIdAndProjectId(Long id, String projectId) {
        return projectParamRepository.existsByIdAndProjectId(id, projectId);
    }

    @Transactional
    public void deleteParam(Long id) {
        projectParamRepository.deleteById(id);
    }

    public boolean paramBelongToUser(String userId, Long id) {
        long result = projectParamRepository.paramIsBelongToUser(id, userId);
        if (result > 0) {
            return true;
        }
        return false;
    }

    public boolean projectBelongToUser(String userId, String id) {
        return repository.existsByIdAndEnableAndCreateBy(id, true, userId);
    }
    public List<Project> findByTypePageable(Integer page, Integer pageSize, EProjectType type) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createAt").descending());
        return repository.findByTypeAndEnable(type, true, pageable);
    }

    public Long increaseView(String id) {
        ProjectView projectView = new ProjectView();
        projectView.setId(0L);
        projectView.setProjectId(id);
        projectView.setCreateAt(Instant.now());
        return projectViewRepository.save(projectView).getId();
    }

    public Project findById(String id) {
        Optional<Project> projectOptional = repository.findByIdAndEnable(id, true);
        return projectOptional.orElse(null);
    }

    public boolean isInterested(String userId, String realEstatePostId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return projectInterestedRepository.existsByDeviceInfoAndProjectId(deviceInfo, realEstatePostId);
        }
        return projectInterestedRepository.existsByUserIdAndProjectId(userId, realEstatePostId);
    }

    public Optional<ProjectInterested> findByDeviceInfoAndProjectId(String deviceInfo, String projectId) {
        return projectInterestedRepository.findByDeviceInfoAndProjectId(deviceInfo, projectId);
    }

    public Optional<ProjectInterested> findByUserIdAndRealEstatePostId(String userId, String projectId) {
        return projectInterestedRepository.findByUserIdAndProjectId(userId, projectId);
    }

    @Transactional
    public ProjectInterested saveInterested(ProjectInterested interested) {
        return projectInterestedRepository.save(interested);
    }

    @Transactional
    public void deleteInterested(Long id) {
        projectInterestedRepository.deleteById(id);
    }

    public ChartOption getChartOption(Integer id, Integer year) {
        ChartOption chartOption = new ChartOption();
        List<IProjectStatistic> response;
        if (id == 1) {
            response = repository.getAllInYear(year);
        } else if (id == 2) {
            response = repository.getByInterestedInYear(year);
        } else if (id == 3) {
            response = repository.getByViewInYear(year);
        } else {
            response = repository.getByCommentInYear(year);
        }
        response
                .stream()
                .parallel()
                .forEach(e -> {
                    chartOption.getXaxis().add(e.getLabel());
                    chartOption.getSeries().add(e.getCnt());
                });
        return chartOption;
    }

    public List<Project> findAllProjectsInterestedByUser(String userId) {
        List<ProjectInterested> projectInterests = projectInterestedRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "createAt"));
        List<Project> response = new ArrayList<>();
        projectInterests
                .stream()
                .forEach(e -> {
                    Optional<Project> projectOptional = repository.findByIdAndEnable(e.getProjectId(), true);
                    if (projectOptional.isPresent()) {
                        response.add(projectOptional.get());
                    }
                });
        return response;
    }
}
