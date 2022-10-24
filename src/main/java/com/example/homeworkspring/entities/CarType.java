package com.example.homeworkspring.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "car_type")
public class CarType {
    @Id
    @Column(name = "name", nullable = false, length = 30)
    private String title;

    public CarType(String title) {
        this.title = title;
    }

    public CarType() {

    }

    @OneToMany(mappedBy = "type")
    private Set<Car> cars = new LinkedHashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

}