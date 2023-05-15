package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Ordering {

    @SerializedName("name_on_card")
    private String nameOnCard;
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("expiration_date")
    private String fullDate;
    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("random")
    private int random;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int productId) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.productId = productId;
    }

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int productId, int quantity) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int productId, int quantity, int random) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.random = random;
    }

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int productId, int quantity, int random, String address, String phone) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.random = random;
        this.address = address;
        this.phone = phone;
    }
}



