package com.example.homeworkspring.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fuels")
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title", nullable = false, length = 25)
    private String title;

    @OneToMany(mappedBy = "fuel")
    private Set<Car> cars = new LinkedHashSet<>();

    public Fuel(String fuel) {
        this.title = fuel;
    }

    public Fuel() {

    }

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