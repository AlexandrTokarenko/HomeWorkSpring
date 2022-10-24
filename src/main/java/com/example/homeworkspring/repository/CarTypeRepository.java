package com.example.homeworkspring.repository;

import com.example.homeworkspring.entities.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarTypeRepository extends JpaRepository<CarType, String> {

    @Query("select c from CarType c where c.title = ?1")
    CarType findByTitle(String title);
}