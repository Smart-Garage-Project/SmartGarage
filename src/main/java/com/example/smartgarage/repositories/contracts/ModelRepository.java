package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findByName(String name);
}
