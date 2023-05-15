package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

;
import com.marwaeltayeb.souq.model.ProductInOnderRespone;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInOrtherRepository {
    private static final String TAG= ProductInOrtherRepository.class.getSimpleName();
    public LiveData<ProductInOnderRespone> getProductInOrders(int userId, String ordernumber) {
        final MutableLiveData<ProductInOnderRespone> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getProInCart(userId, ordernumber).enqueue(new Callback<ProductInOnderRespone>() {

            @Override
            public void onResponse(Call<ProductInOnderRespone> call, Response<ProductInOnderRespone> response) {
                Log.d("onResponse", "" + response.code());
                ProductInOnderRespone responseBody = response.body();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ProductInOnderRespone> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return mutableLiveData;
    }
}
