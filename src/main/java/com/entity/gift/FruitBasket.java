package com.entity.gift;

import com.entity.gift.fruit.Fruit;

import java.util.Set;

public class FruitBasket extends Gift {



    private Set<Fruit> fruits;

    public Set<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(Set<Fruit> fruits) {
        this.fruits = fruits;
    }
}
