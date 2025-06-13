package com.api.BlogAppApi.repository;

import com.api.BlogAppApi.model.MultaDB;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MultaDBRepository extends JpaRepository<MultaDB, Long> {

    List<MultaDB> findByStatus(String status);

}
