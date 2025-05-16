package com.api.BlogAppApi.repository;

import com.api.BlogAppApi.model.BlogAppPostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogAppPostRepository extends JpaRepository<BlogAppPostModel, Long> {
}
