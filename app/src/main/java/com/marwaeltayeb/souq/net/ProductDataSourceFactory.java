package com.marwaeltayeb.souq.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;

public class ProductDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource productDataSource;

    private final String category;
    private final int userId;

    public ProductDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        productDataSource = new ProductDataSource(category, userId);

        // Posting the Data source to get the values
        productLiveDataSource.postValue(productDataSource);

        // Returning the Data source
        return productDataSource;
    }
}
//Đoạn code này tạo ra một lớp ProductDataSourceFactory trong gói com.marwaeltayeb.souq.net,
//lớp này mở rộng lớp DataSource.Factory. Đối tượng của lớp này sẽ được sử dụng để cung cấp một danh sách các sản phẩm phân trang.
//Lớp ProductDataSourceFactory có một hàm tạo, nhận vào một chuỗi category và một số nguyên userId. Những tham số này được sử dụng
// để tạo đối tượng ProductDataSource sau đó.
//Lớp cũng tạo ra một đối tượng MutableLiveData có tên là productLiveDataSource, đối tượng này sẽ chứa đối tượng ProductDataSource
// sẽ được sử dụng sau này.
//Phương thức create() của lớp này được ghi đè để tạo ra một đối tượng ProductDataSource, đối tượng này được sử dụng để cung cấp dữ
// liệu phân trang cho danh sách sản phẩm. Sau đó, đối tượng ProductDataSource này được đưa vào productLiveDataSource, và trả về để được sử dụng trong các thành phần khác của ứng dụng.