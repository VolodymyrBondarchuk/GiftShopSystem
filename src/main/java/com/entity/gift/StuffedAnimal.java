package com.entity.gift;

import com.entity.gift.animal.Animal;

import java.util.Set;

public class StuffedAnimal extends Gift {

    private Set<Animal> animals;

    private double fee = 3.99;

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
