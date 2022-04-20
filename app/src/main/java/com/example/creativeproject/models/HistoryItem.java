package com.example.creativeproject.models;

import java.util.ArrayList;
import java.util.List;

public class HistoryItem {
    private String date;
    private List<Product> productList = new ArrayList<>();

    public void setList(List<Product> productList) {
        this.productList.addAll(productList);
    }

    public void setDate (String date){
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
