// Created by Deek
// Creation date 12/12/2021

package com.example.project_bigbangk.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class PriceHistory implements Comparable<PriceHistory> {


    private Asset asset;
    private LocalDateTime dateTime;
    private double price;

    public PriceHistory(LocalDateTime dateTime, double price, Asset asset) {
        super();
        this.dateTime = dateTime;
        this.asset = asset;
        this.price = price;
    }

    public PriceHistory(LocalDateTime dateTime, double price) {
        super();
        this.dateTime = dateTime;
        this.price = price;
    }

    public Asset getAsset() {
        return asset;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(PriceHistory o) {
        return this.getDateTime().compareTo(o.getDateTime());
    }
}