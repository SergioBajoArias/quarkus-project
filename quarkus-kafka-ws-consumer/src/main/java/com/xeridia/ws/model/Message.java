package com.xeridia.ws.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigInteger;
import java.sql.Timestamp;

@RegisterForReflection
public class Message {
    Integer id;
    String product;
    double price;
    Timestamp recordTime;

    public Message() {}

    public Message(Integer id, String product, double price) {
        this.id = id;
        this.product = product;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }
}
