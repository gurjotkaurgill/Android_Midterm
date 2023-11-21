package com.example.midterm;

import android.graphics.Bitmap;

public class Product {
    private String productName;
    private Bitmap productImage;

    public Product(String productName, Bitmap productImage) {
        this.productName = productName;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
    }
}
