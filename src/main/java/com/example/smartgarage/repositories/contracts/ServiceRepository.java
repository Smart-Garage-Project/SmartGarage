package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.ServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {

    @Query("""
            SELECT DISTINCT sm FROM ServiceModel AS sm
            WHERE sm.car.id = :carId
            AND (:fromDate IS NULL OR sm.date >= :fromDate)
            AND (:toDate IS NULL OR sm.date <= :toDate)
            ORDER BY sm.date DESC
            """)
    Page<ServiceModel> findCarServices(@Param("carId") int carId,
                                       @Param("fromDate") LocalDate fromDate,
                                       @Param("toDate") LocalDate toDate,
                                       Pageable pageable);
}
