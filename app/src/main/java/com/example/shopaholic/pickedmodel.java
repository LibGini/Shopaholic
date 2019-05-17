package com.example.shopaholic;


public class pickedmodel  extends postID {
    private String product_image,product_details,produtc_prize,product_review,product_pick;

    public pickedmodel() {
    }

    public pickedmodel(String product_image, String product_details, String produtc_prize, String product_review, String product_pick) {
        this.product_image = product_image;
        this.product_details = product_details;
        this.produtc_prize = produtc_prize;
        this.product_review = product_review;
        this.product_pick = product_pick;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_details() {
        return product_details;
    }

    public String getProdutc_prize() {
        return produtc_prize;
    }

    public String getProduct_review() {
        return product_review;
    }

    public String getProduct_pick() {
        return product_pick;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public void setProdutc_prize(String produtc_prize) {
        this.produtc_prize = produtc_prize;
    }

    public void setProduct_review(String product_review) {
        this.product_review = product_review;
    }

    public void setProduct_pick(String product_pick) {
        this.product_pick = product_pick;
    }


}
