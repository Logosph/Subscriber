package com.example.subscriber.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscription_table")
public class SubscriptionItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private final String name;
    private final String description;
    private final Integer every;
    private final String period;
    private final Double price;
    private final String currency;

    private final String note;
    private final String date;

    public SubscriptionItem(String name, String description, Integer every, String period, Double price, String currency, String note, String date) {
        this.name = name;
        this.description = description;
        this.every = every;
        this.period = period;
        this.price = price;
        this.currency = currency;
        this.note = note;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getEvery() {
        return every;
    }

    public String getPeriod() {
        return period;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SubscriptionItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", every=" + every +
                ", period='" + period + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
