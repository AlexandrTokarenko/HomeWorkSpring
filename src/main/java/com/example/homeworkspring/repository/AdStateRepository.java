package com.example.homeworkspring.repository;

import com.example.homeworkspring.entities.AdState;
import com.example.homeworkspring.entities.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdStateRepository extends JpaRepository<AdState, String> {

    @Query("select a from AdState a where a.title = ?1")
    AdState findByTitle(String title);
}