package com.marwaeltayeb.souq.net;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.model.ProductApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {

    private static final String TAG = "ProductDataSource";
    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private final String category;
    private final int userId;

    ProductDataSource(String category, int userId) {
        this.category = category;
        this.userId = userId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductsByCategory(category, userId,FIRST_PAGE)
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
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductsByCategory(category,userId,params.key)
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
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductsByCategory(category,userId,params.key)
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
//Đây là mã Java cho một PageKeyedDataSource, là một phần của thư viện Paging của Android Jetpack. Mục đích của lớp này là cung cấp dữ liệu cho một danh sách được phân trang trong ứng dụng Android. Nó được sử dụng để tải dữ liệu theo từng trang và giữ dữ liệu trong bộ nhớ đệm để tránh tải lại dữ liệu khi người dùng cuộn danh sách.
//
//Lớp ProductDataSource này lấy dữ liệu về sản phẩm từ API thông qua RetrofitClient và trả về danh sách sản phẩm dưới dạng PageKeyedDataSource. Nó sử dụng phương thức loadInitial() để tải dữ liệu của trang đầu tiên và phương thức loadAfter() và loadBefore() để tải các trang tiếp theo hoặc trước đó.
//
//Các biến FIRST_PAGE, PAGE_SIZE, category và userId là các tham số được sử dụng để truyền thông tin tải dữ liệu từ API. Log.v() được sử dụng để ghi lại thông tin về việc tải dữ liệu hoặc xảy ra lỗi. Các phương thức onResponse() và onFailure() được triệu gọi khi kết quả trả về từ API được xử lý hoặc xảy ra lỗi trong quá trình tải dữ liệu.
