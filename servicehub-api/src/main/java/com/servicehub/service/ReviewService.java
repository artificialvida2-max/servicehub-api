package com.servicehub.service;

import com.servicehub.model.Review;
import com.servicehub.repository.ReviewRepository;
import com.servicehub.util.SanitizerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repo;

    @PreAuthorize("isAuthenticated()")
    public List<Review> listar() {
        return repo.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    public Review criar(Review r) {
        if (r.getComment() != null) {
            r.setComment(SanitizerUtil.sanitize(r.getComment()));
        }
        return repo.save(r);
    }

    @PreAuthorize("isAuthenticated()")
    public Review buscar(Long id) {
        return repo.findById(id).orElse(null);
    }
}
