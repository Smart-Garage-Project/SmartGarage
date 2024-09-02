package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT c FROM Car AS c WHERE c.owner.id = :userId")
    List<Car> findUserCars(@Param("userId") int userId);

}

