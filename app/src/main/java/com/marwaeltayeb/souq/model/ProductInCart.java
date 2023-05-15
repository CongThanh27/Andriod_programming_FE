package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductInCart implements Serializable {
    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("image")
    private String productImage;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("quantity")
    private int productQuantity;
    @SerializedName("supplier")
    private String productSupplier;
    @SerializedName("cartquantity")
    private int cartquantity;
    @SerializedName("username")
    private String username;
    @SerializedName("isFavourite")
    private int isFavourite;
    @SerializedName("isInCart")
    private int isInCart;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    public int getCartquantity() {
        return cartquantity;
    }

    public void setCartquantity(int cartquantity) {
        this.cartquantity = cartquantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int isFavourite() {
        return isFavourite;
    }


    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite ? 1 : 0;
    }
    public int isInCart() {
        return isInCart;
    }
    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart ? 1 : 0;
    }
}

