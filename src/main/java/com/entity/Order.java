package com.entity;

import com.entity.gift.Gift;

import java.io.Serializable;
import java.util.*;

public class Order implements Serializable {

    private String id;

    private String deliveryDate;

    private String consumerName;

    private String address;

    private String email;

    private String message;

    private double price;

    private boolean hasSent = false;

    private int curId = 1;

    Map<Integer, Gift> gifts = new HashMap<Integer, Gift>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(message.length() > 20) {
            throw new IllegalArgumentException();
        } else {
            this.message = message;
        }

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int addGift(Gift gift) {
        if(gifts.isEmpty()) {
            this.gifts.put(curId, gift);
            this.price+=gift.getPrice();

            return curId;
        } else {
            curId++;
            this.gifts.put(curId, gift);
            this.price+=gift.getPrice();

            return curId;
        }

    }

    public Map<Integer, Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Map<Integer, Gift> gifts) {
        this.gifts = gifts;
    }

    public boolean isHasSent() {
        return hasSent;
    }

    public void setHasSent(boolean hasSent) {
        this.hasSent = hasSent;
    }
}
