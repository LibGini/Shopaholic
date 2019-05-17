package com.example.shopaholic;

public class CartModel extends postID{
    private String product_image,user_id,product_id,product_name;
    private Integer product_prize;

    public CartModel() {
    }

    public CartModel(String product_image, String user_id, String product_id, String product_name, Integer product_prize) {
        this.product_image = product_image;
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_prize = product_prize;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Integer getProduct_prize() {
        return product_prize;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_prize(Integer product_prize) {
        this.product_prize = product_prize;
    }
}
