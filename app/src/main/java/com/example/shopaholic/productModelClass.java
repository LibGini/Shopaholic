package com.example.shopaholic;

import java.util.List;

public class productModelClass extends postID{
    String description,category,product_image,product_name,user_id;
    Integer product_prize,stock;

    public productModelClass() {
    }

    public productModelClass(String description, String category, String product_image, String product_name, String user_id, Integer product_prize, Integer stock) {
        this.description = description;
        this.category = category;
        this.product_image = product_image;
        this.product_name = product_name;
        this.user_id = user_id;
        this.product_prize = product_prize;
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public Integer getProduct_prize() {
        return product_prize;
    }

    public Integer getStock() {
        return stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setProduct_prize(Integer product_prize) {
        this.product_prize = product_prize;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
