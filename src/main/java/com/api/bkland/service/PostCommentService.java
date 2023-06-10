package com.api.bkland.service;

import com.api.bkland.entity.PostComment;
import com.api.bkland.entity.response.ICommentCompare;
import com.api.bkland.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostCommentService {
    @Autowired
    private PostCommentRepository repository;

    @Transactional
    public PostComment save(PostComment postComment) {
        return repository.save(postComment);
    }

    public List<PostComment> findByPostId(String postId) {
        return repository.findByPostId(postId);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean canDelete(Long id, String userId) {
        if (userId.equals("admin")) {
            return true;
        }
        Optional<ICommentCompare> commentCompareOptional = repository.compareOwner(id);
        if (commentCompareOptional.isEmpty()) {
            return false;
        }
        ICommentCompare commentCompare = commentCompareOptional.get();
        if (userId.equals(commentCompare.getCommentOwner()) || userId.equals(commentCompare.getPostOwner())) {
            return true;
        }
        return false;
    }
}
