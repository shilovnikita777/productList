package com.example.creativeproject.models;

import androidx.annotation.NonNull;

public class Product {
    private String name;
    private double count;
    private String measure;
    private int imageID;

    public Product(String name, double count, String measure,int imageID) {
        this.name = name;
        this.count = count;
        this.measure = measure;
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @NonNull
    @Override
    public String toString() {
        return "Имя: " + name +", Количество: " + count + ", Мера: "+ measure + "\n";
    }
}
