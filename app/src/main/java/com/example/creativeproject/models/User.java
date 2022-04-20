package com.example.creativeproject.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final User instance = new User();
    private String name;
    private List<Product> currentCreatingProductList = new ArrayList<>();
    private List<Product> needToAddProductList = new ArrayList<>();
    private List<Product> alreadyInCartProductList = new ArrayList<>();

    private String currentListDate;
    private List<HistoryItem> historyProductList = new ArrayList<>();

    private User() {}

    public List<Product> getCurrentCreatingProductList() {
        return currentCreatingProductList;
    }

    public List<Product> getNeedToAddProductList() {
        return needToAddProductList;
    }

    public List<Product> getAlreadyInCartProductList() {
        return alreadyInCartProductList;
    }

    public void setCurrentCreatingProductList(List<Product> currentCreatingProductList) {
        this.currentCreatingProductList = currentCreatingProductList;
    }

    public void copyToNeedProductList(List<Product> needToAddProductList) {
        this.needToAddProductList.addAll(needToAddProductList);
    }

    public static User getInstance(){
        return instance;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentListDate() {
        return currentListDate;
    }

    public void setCurrentListDate(String currentListDate) {
        this.currentListDate = currentListDate;
    }

    public List<HistoryItem> getHistoryProductList() {
        return historyProductList;
    }
}
