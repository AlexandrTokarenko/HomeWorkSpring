package com.example.homeworkspring.repository;

import com.example.homeworkspring.entities.Car;
import com.example.homeworkspring.entities.CarType;
import com.example.homeworkspring.entities.Fuel;
import com.example.homeworkspring.entities.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("select c from Car c where c.user.id = ?1 ")
    List<Car> findByUserId(int id);

    @Query("""
            select c from Car c
            where c.brand like ?1 and c.model like ?2 and c.price between ?3 and ?4 and c.year between ?5 and ?6 and c.mileage between ?7 and ?8 and c.engineVolume between ?9 and ?10 and c.type = ?11 and c.fuel = ?12 and c.transmission = ?13""")
    List<Car> findByBrandLikeAndModelLikeAndPriceBetweenAndYearBetweenAndMileageBetweenAndEngineVolumeBetweenAndTypeAndFuelAndTransmission(String brand,
                                                                                                                                           String model,
                                                                                                                                           int firstPrice,
                                                                                                                                           int lastPrice,
                                                                                                                                           int firstYear,
                                                                                                                                           int lastYear,
                                                                                                                                           int firstMileage,
                                                                                                                                           int lastMileage,
                                                                                                                                           double firstEngineVolume,
                                                                                                                                           double lastEngineVolume,
                                                                                                                                           CarType type, Fuel fuel,
                                                                                                                                           Transmission transmission
                                                                                                                     );
    @Query("""
            select c from Car c
            where c.brand like ?1 and c.model like ?2 and c.price between ?3 and ?4 and c.year between ?5 and ?6 and c.mileage between ?7 and ?8 and c.engineVolume between ?9 and ?10""")
    List<Car> findByBrandLikeAndModelLikeAndPriceBetweenAndYearBetweenAndMileageBetweenAndEngineVolumeBetween(String brand,
                                                                                                              String model,
                                                                                                              int firstPrice,
                                                                                                              int lastPrice,
                                                                                                              int firstYear,
                                                                                                              int lastYear,
                                                                                                              int firstMileage,
                                                                                                              int lastMileage,
                                                                                                              double firstEngineVolume,
                                                                                                              double lastEngineVolume);
}