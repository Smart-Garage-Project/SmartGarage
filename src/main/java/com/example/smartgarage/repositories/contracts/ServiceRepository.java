package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {

    @Query("SELECT sm FROM ServiceModel AS sm WHERE sm.car.id = :carId")
    List<ServiceModel> findCarServices(@Param("carId") int carId);

}
