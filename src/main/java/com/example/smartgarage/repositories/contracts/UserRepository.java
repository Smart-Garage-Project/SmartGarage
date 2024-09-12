package com.example.smartgarage.repositories.contracts;

import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("""
            SELECT DISTINCT u FROM User AS u
            WHERE (:username IS NULL OR u.username LIKE %:username%)
            AND (:email IS NULL OR u.email LIKE %:email%)
            AND (:phoneNumber IS NULL OR u.phoneNumber LIKE %:phoneNumber%)
            """)
    Page<User> findUsersByCriteria(@Param("username") String username,
                                   @Param("email") String email,
                                   @Param("phoneNumber") String phoneNumber,
                                   Pageable pageable);

}
