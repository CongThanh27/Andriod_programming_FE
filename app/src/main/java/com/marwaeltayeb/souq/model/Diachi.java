package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Diachi implements Serializable {
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
