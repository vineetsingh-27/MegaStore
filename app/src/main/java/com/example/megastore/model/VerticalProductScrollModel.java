package com.example.megastore.model;

public class VerticalProductScrollModel {
    private int productImage;
    private String productTitle;
    private String productPrice;

    public VerticalProductScrollModel(int productImage, String productTitle, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
