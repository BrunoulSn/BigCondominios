package com.api.BigCondominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.BigCondominio.model.MultaDB;

import java.util.List;

public interface MultaDBRepository extends JpaRepository<MultaDB, Long> {

    List<MultaDB> findByStatus(String status);

}
