package com.api.BlogAppApi.repository;

import com.api.BlogAppApi.model.ReservaDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaDBRepository extends JpaRepository<ReservaDB, Long> {
}
