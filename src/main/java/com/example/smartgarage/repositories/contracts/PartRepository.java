package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {

    @Query("SELECT p FROM Part AS p WHERE p.id NOT IN :excludedPartIds")
    List<Part> findAllExcluded(@Param("excludedPartIds") List<Integer> excludedPartIds);

    @Query("SELECT p FROM Part AS p")
    Page<Part> findAllPaged(Pageable pageable);
}
