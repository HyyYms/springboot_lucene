package com.lucene.springboot_lucene;

import org.apache.lucene.search.IndexSearcher;

public class Product {
    Integer id;
    String name;
    String category;
    float price;
    String place;
    String code;

    public Product() {
    }

    public Product(Integer id, String name, String category, float price, String place, String code) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.place = place;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", place='" + place + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
