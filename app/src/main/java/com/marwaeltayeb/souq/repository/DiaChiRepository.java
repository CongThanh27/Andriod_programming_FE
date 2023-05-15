package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.DiaChiApiResponse;

import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaChiRepository {
    private static final String TAG = DiaChiRepository.class.getSimpleName();
    public LiveData<DiaChiApiResponse> getDiaChi(int userId) {
        final MutableLiveData<DiaChiApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getDiaChi(userId).enqueue(new Callback<DiaChiApiResponse>() {
            @Override
            public void onResponse(Call<DiaChiApiResponse> call, Response<DiaChiApiResponse> response) {
                Log.d("onResponse", "" + response.code());
                DiaChiApiResponse responseBody = response.body();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }
            @Override
            public void onFailure(Call<DiaChiApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
