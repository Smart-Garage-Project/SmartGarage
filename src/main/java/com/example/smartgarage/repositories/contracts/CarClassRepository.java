package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.CarClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarClassRepository extends JpaRepository<CarClass, Integer> {

    Optional<CarClass> findByName(String name);
}
