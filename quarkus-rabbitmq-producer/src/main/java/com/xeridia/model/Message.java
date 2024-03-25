package com.xeridia.model;

import java.util.UUID;

public class Message {
    Long id;
    String product;
    double price;

    public Message() {}

    public Message(Long id, String product, double price) {
        this.id = id;
        this.product = product;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
