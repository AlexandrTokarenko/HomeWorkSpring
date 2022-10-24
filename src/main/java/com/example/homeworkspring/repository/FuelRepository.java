package com.example.homeworkspring.repository;

import com.example.homeworkspring.entities.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuelRepository extends JpaRepository<Fuel, String> {

    @Query("select f from Fuel f where f.title = ?1")
    Fuel findByTitle(String title);
}