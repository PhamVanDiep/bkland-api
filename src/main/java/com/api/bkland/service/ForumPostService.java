package com.api.bkland.service;

import com.api.bkland.entity.ForumPost;
import com.api.bkland.entity.ForumPostLike;
import com.api.bkland.payload.response.ForumPostLog;
import com.api.bkland.repository.ForumPostLikeRepository;
import com.api.bkland.repository.ForumPostRepository;
import com.api.bkland.repository.PostCommentRepository;
import com.api.bkland.repository.PostReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ForumPostService {
    @Autowired
    private ForumPostRepository repository;

    @Autowired
    private ForumPostLikeRepository forumPostLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostReportRepository postReportRepository;

    @Transactional
    public void save(ForumPost forumPost, String userId, boolean isUpdate) {
        if (isUpdate) {
            forumPost.setUpdateBy(userId);
            forumPost.setUpdateAt(Instant.now());
        } else {
            forumPost.setCreateAt(Instant.now());
            forumPost.setCreateBy(userId);
        }
        repository.save(forumPost);
    }

    public boolean existsById(String id) {
        return repository.existsByIdAndEnable(id, true);
    }

    public ForumPost findById(String id) {
        Optional<ForumPost> forumPost = repository.findByIdAndEnable(id, true);
        if (forumPost.isEmpty()) {
            return null;
        }
        return forumPost.get();
    }

    public Page<ForumPost> findByUser(String userId, Integer pageSize, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createAt").descending());
        return repository.findByCreateByAndEnable(userId, true, pageable);
    }

    public Page<ForumPost> findAllWithPageable(Integer pageSize, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createAt").descending());
        return repository.findByEnable(true, pageable);
    }

    @Transactional
    public boolean like(String forumPostId, String userId) {
        if (forumPostLikeRepository.existsByForumPostIdAndUserId(forumPostId, userId)) {
            forumPostLikeRepository.deleteByForumPostIdAndUserId(forumPostId, userId);
            return false;
        } else {
            ForumPostLike forumPostLike = new ForumPostLike();
            forumPostLike.setForumPostId(forumPostId);
            forumPostLike.setUserId(userId);
            forumPostLike.setCreateBy(userId);
            forumPostLike.setCreateAt(Instant.now());

            forumPostLikeRepository.save(forumPostLike);
            return true;
        }
    }


    public boolean isLiked(String postId, String userId) {
        return forumPostLikeRepository.existsByForumPostIdAndUserId(postId, userId);
    }

    public ForumPostLog getLog(String postId) {
        ForumPostLog forumPostLog = new ForumPostLog();
        forumPostLog.setNoLikes(forumPostLikeRepository.countByForumPostId(postId));
        forumPostLog.setNoComments(postCommentRepository.countByPostId(postId));
        forumPostLog.setNoReports(postReportRepository.countByPostId(postId));
        return forumPostLog;
    }

    public Object findAllNotByAdmin() {
        return repository.findAllByUser();
    }

    @Transactional
    public void deleteById(String postId) {
        repository.deletePostById(postId);
    }
}
