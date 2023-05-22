package com.api.bkland.service;

import com.api.bkland.entity.InfoPost;
import com.api.bkland.repository.InfoPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfoPostService {
    @Autowired
    private InfoPostRepository repository;

    public List<InfoPost> findAll() {
        List<InfoPost> infoPosts = repository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));
        return infoPosts;
    }

    public InfoPost update(InfoPost infoPost) {
        InfoPost infoPost1 = repository.save(infoPost);
        return infoPost1;
    }

    public InfoPost create(InfoPost infoPost) {
        InfoPost infoPost1 = repository.save(infoPost);
        return infoPost1;
    }

    public InfoPost findById(Long id) {
        Optional<InfoPost> infoPostOptional = repository.findById(id);
        if (infoPostOptional.isEmpty()) {
            return null;
        }
        return infoPostOptional.get();
    }
}
