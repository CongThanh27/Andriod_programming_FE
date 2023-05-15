package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductInOrder implements Serializable {
    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("status")
    private String orderDateStatus;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("id")
    private int productId;
    @SerializedName("image")
    private String image;
    @SerializedName("address")
    private String shippingAddress;
    @SerializedName("phone")
    private String shippingPhone;

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderDateStatus() {
        return orderDateStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getImage() {
        return image;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }
}
