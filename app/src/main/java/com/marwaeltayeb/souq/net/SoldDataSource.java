package com.marwaeltayeb.souq.net;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.model.ProductApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoldDataSource extends PageKeyedDataSource<Integer, Product> {
    private static final String TAG = "SoldDataSource";
    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private final int userId;

    public SoldDataSource(int userId) {
        this.userId = userId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductsSold( userId,FIRST_PAGE)
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {
                        Log.v(TAG, "Succeeded " + response.body().getProducts().size());

                        if (response.body().getProducts() == null) {
                            return;
                        }

                        if (response.body() != null) {
                            callback.onResult(response.body().getProducts(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {
                        Log.v(TAG, "Failed to get Products");
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductsSold(userId,params.key)
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {
                            // Passing the loaded database and the previous page key
                            callback.onResult(response.body().getProducts(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {
                        Log.v(TAG, "Failed to previous Products");
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductsSold(userId,params.key)
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {
                        if (response.body() != null) {
                            // If the response has next page, increment the next page number
                            Integer key = response.body().getProducts().size() == PAGE_SIZE ? params.key + 1 : null;

                            // Passing the loaded database and next page value
                            callback.onResult(response.body().getProducts(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {
                        Log.v(TAG, "Failed to get next Products");
                    }
                });
    }
}
