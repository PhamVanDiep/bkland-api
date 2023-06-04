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

    public ForumPost findById(String id) {
        Optional<ForumPost> forumPost = repository.findById(id);
        if (forumPost.isEmpty()) {
            return null;
        }
        return forumPost.get();
    }

    public Page<ForumPost> findByUser(String userId, Integer pageSize, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createAt").descending());
        return repository.findByCreateBy(userId, pageable);
    }

    public Page<ForumPost> findAllWithPageable(Integer pageSize, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createAt").descending());
        return repository.findAll(pageable);
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

    public boolean existsById(String postId) {
        return repository.existsById(postId);
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
}
