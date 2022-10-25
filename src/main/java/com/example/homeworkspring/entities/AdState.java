package com.example.homeworkspring.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ad_state")
public class AdState {
    @Id
    @Column(name = "title", nullable = false, length = 20)
    private String title;

    public AdState(String title) {
        this.title = title;
    }

    public AdState() {

    }
    @OneToMany(mappedBy = "condition")
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