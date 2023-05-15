package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiaChiApiResponse {
    @SerializedName("shippingInfo")
    private List<Diachi> shippingInfo;

    public List<Diachi> getDiaChiInfo() {
        return shippingInfo;
    }
}
