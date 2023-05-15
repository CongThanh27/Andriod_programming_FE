package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartApiResponse {

    @SerializedName("carts")
    private List<ProductInCart> carts;

    public List<ProductInCart> getProductsInCart() {
        return carts;
    }
}
