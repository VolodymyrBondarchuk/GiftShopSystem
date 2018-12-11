package com.entity.gift;

import com.enums.GiftSize;
import com.exceptions.IllegalOrderSizeException;

import java.io.Serializable;

public abstract class Gift implements Serializable {

    private Double price;
    private GiftSize size;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public GiftSize getSize() {
        return size;
    }

    public void setSize(String size) {
        if(size.equals("S")) {
            this.size = GiftSize.S;
        } else

        if(size.equals("M")) {
            this.size = GiftSize.M;
        } else

        if(size.equals("L")) {
            this.size = GiftSize.L;
        }

        else {
            throw new IllegalOrderSizeException();
        }

    }
}
