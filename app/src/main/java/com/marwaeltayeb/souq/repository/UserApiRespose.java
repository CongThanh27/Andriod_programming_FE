package com.marwaeltayeb.souq.repository;

import com.google.gson.annotations.SerializedName;
import com.marwaeltayeb.souq.model.User;


import java.util.List;

public class UserApiRespose {
    @SerializedName("user")
    private List<User> user;

    public List<User> getUser() {
        return user;
    }
}
