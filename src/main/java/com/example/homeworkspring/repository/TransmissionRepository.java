package com.example.homeworkspring.repository;

import com.example.homeworkspring.entities.Car;
import com.example.homeworkspring.entities.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransmissionRepository extends JpaRepository<Transmission, String> {

    @Query("select t from Transmission t where t.title = ?1")
    Transmission findByTitle(String title);
}