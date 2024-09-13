package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT c FROM Car AS c WHERE c.owner.id = :userId")
    Page<Car> findUserCars(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT DISTINCT c FROM Car AS c " +
            "WHERE (:brand IS NULL OR c.brand.name LIKE %:brand%) " +
            "AND (:model IS NULL OR c.model.name LIKE %:model%) " +
            "AND (:licensePlate IS NULL OR c.licensePlate LIKE %:licensePlate%)")
    Page<Car> findAllFiltered(@Param("brand") String brand,
                              @Param("model") String model,
                              @Param("licensePlate")String licensePlate,
                              Pageable pageable);

    @Query("SELECT c FROM Car AS c WHERE c.licensePlate = :licensePlate")
    Optional<Car> findByLicensePlate(@Param("licensePlate") String licensePlate);
}

