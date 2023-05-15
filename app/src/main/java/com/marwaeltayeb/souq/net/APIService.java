package com.marwaeltayeb.souq.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.repository.UserApiRespose;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
//Api endpoint là API của server mà bạn cần sử dụng để yêu cầu dữ liệu từ serve
public interface APIService {
    //B2:
    //Tạo ra một đối tượng Gson, được sử dụng để chuyển đổi các đối tượng Java thành định dạng JSON và ngược lại.
    Gson gson = new GsonBuilder()
            //GsonBuilder để tạo ra một đối tượng Gson và cấu hình nó để định dạng ngày tháng được định dạng theo chuẩn "yyyy-MM-dd HH:mm:ss".
            // Điều này đảm bảo rằng khi chuyển đổi đối tượng Java thành JSON, các giá trị ngày tháng được định dạng đúng cách,
            // và khi chuyển đổi từ JSON thành đối tượng Java, các giá trị ngày tháng được phân tích đúng cách.
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    //tạo ra một đối tượng APIService, được sử dụng để gửi các yêu cầu mạng đến một API.
    APIService apiService = new Retrofit.Builder()// Retrofit.Builder để tạo ra một đối tượng Retrofit
            //cấu hình Retrofit để sử dụng đường dẫn cơ bản của API
            .baseUrl("http://192.168.174.227:3000/")
            //sử dụng GsonConverterFactory để chuyển đổi giữa định dạng JSON và đối tượng Java.
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
    //Annotation HTTP cung cấp request method và
    //URL. Có năm Annotation được tích hợp
    //sẵn: @GET, @POST, @PUT, @DELETE và @HEAD
    //b2: Tạo các phương thức truy vấn
    @GET("users/thongtindathang")
    Call<User> getUserInFo(@Query("id") int userId);
}

//