package com.api.BigCondominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.BigCondominio.model.ReservaDB;

public interface ReservaDBRepository extends JpaRepository<ReservaDB, Long> {
}
