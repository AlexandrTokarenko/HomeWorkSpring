package com.example.homeworkspring.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "transmission")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title", nullable = false, length = 25)
    private String title;

    @OneToMany(mappedBy = "transmission")
    private Set<Car> cars = new LinkedHashSet<>();

    public Transmission(String transmission) {
        this.title = transmission;
    }

    public Transmission() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String id) {
        this.title = id;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

}