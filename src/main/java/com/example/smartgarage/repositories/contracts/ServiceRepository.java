package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.ServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {

    @Query("SELECT sm FROM ServiceModel AS sm WHERE sm.car.id = :carId")
    Page<ServiceModel> findCarServices(@Param("carId") int carId, Pageable pageable);

}
