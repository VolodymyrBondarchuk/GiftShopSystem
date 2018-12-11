package com.enums;

public enum GiftSize {


    S(19.99), M(29.99), L(39.99);

    private double fee;


    GiftSize(double fee) {
            this.fee = fee;
        }

        public double fee(){
            return fee;
        }
}
