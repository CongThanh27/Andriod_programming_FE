package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;
import com.marwaeltayeb.souq.model.ProductInOrder;

import java.util.List;

public class ProductInOnderRespone {
    @SerializedName("productsinorder")
    private List<ProductInOrder> productList;

    public List<ProductInOrder> getProductListInOrderList() {
        return productList;
    }
}
