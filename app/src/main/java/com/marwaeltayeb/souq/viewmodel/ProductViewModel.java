package com.marwaeltayeb.souq.viewmodel;

import static com.marwaeltayeb.souq.net.LaptopDataSourceFactory.laptopDataSource;
import static com.marwaeltayeb.souq.net.ProductDataSourceFactory.productDataSource;
import static com.marwaeltayeb.souq.net.FlashSaleDataSourceFactory.flashSaleDataSource;
import static com.marwaeltayeb.souq.net.FavoriteDataSourceFactory.favoriteDataSource;
import static com.marwaeltayeb.souq.net.SoldDataSourceFactory.soldDataSource;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.FavoriteDataSourceFactory;
import com.marwaeltayeb.souq.net.FlashSaleDataSourceFactory;
import com.marwaeltayeb.souq.net.LaptopDataSourceFactory;
import com.marwaeltayeb.souq.net.ProductDataSource;
import com.marwaeltayeb.souq.net.FlashSaleDataSource;
import com.marwaeltayeb.souq.net.ProductDataSourceFactory;
import com.marwaeltayeb.souq.net.SoldDataSourceFactory;

public class ProductViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> productPagedList;
    public LiveData<PagedList<Product>> flashSalePagedList;
    public LiveData<PagedList<Product>> laptopPagedList;

    public LiveData<PagedList<Product>> favoritePagedList;
    public LiveData<PagedList<Product>> soldPagedList;
    // Get PagedList configuration
    private static final PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();
    private static final PagedList.Config  pagedListConfig1 =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(FlashSaleDataSource.PAGE_SIZE)
                    .build();

    public void loadMobiles(String category, int userId){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);
        productPagedList = (new LivePagedListBuilder<>(productDataSourceFactory, pagedListConfig)).build();
    }
    public void loadFavorite( int userId){
        // Get our database source factory
        FavoriteDataSourceFactory favoriteDataSourceFactory = new FavoriteDataSourceFactory(userId);
        favoritePagedList = (new LivePagedListBuilder<>(favoriteDataSourceFactory, pagedListConfig)).build();
    }
    public void loadSold( int userId){
        // Get our database source factory
        SoldDataSourceFactory soldDataSourceFactory = new SoldDataSourceFactory(userId);
        soldPagedList = (new LivePagedListBuilder<>(soldDataSourceFactory, pagedListConfig)).build();
    }

    public void loadFlashSale(int userId) {
        // Get our database source factory
        FlashSaleDataSourceFactory flashSaleDataSourceFactory = new FlashSaleDataSourceFactory(userId);
        flashSalePagedList = (new LivePagedListBuilder<>(flashSaleDataSourceFactory, pagedListConfig1)).build();
    }
    public void loadLaptops(String category, int userId){
        // Get our database source factory
        LaptopDataSourceFactory laptopDataSourceFactory = new LaptopDataSourceFactory(category,userId);
        // Build the paged list
        laptopPagedList = (new LivePagedListBuilder<>(laptopDataSourceFactory, pagedListConfig)).build();
    }
    public void invalidate(){
        if(flashSaleDataSource!= null) flashSaleDataSource.invalidate();
        if(productDataSource != null) productDataSource.invalidate();
        if(laptopDataSource!= null) laptopDataSource.invalidate();
        if(favoriteDataSource!= null) favoriteDataSource.invalidate();
        if(soldDataSource!= null) soldDataSource.invalidate();
    }
}
//Đoạn mã này tạo ra một lớp ProductViewModel trong gói com.marwaeltayeb.souq.viewmodel.
// Lớp này mở rộng lớp ViewModel của Android Architecture Components và cung cấp các phương thức để tải danh sách
// sản phẩm phân trang từ nguồn dữ liệu khác nhau.
//
//Lớp ProductViewModel có hai thuộc tính productPagedList và laptopPagedList kiểu LiveData<PagedList<Product>>.
// Đây là các danh sách sản phẩm phân trang sẽ được sử dụng để hiển thị các sản phẩm trên giao diện người dùng.
//
//Phương thức loadMobiles và loadLaptops được sử dụng để tải danh sách sản phẩm phân trang cho các danh mục tương ứng.
// Đầu vào cho phương thức này là một chuỗi category và một số nguyên userId. Để tải dữ liệu, phương thức này sử
// dụng các đối tượng ProductDataSourceFactory và LaptopDataSourceFactory để tạo ra đối tượng ProductDataSource và LaptopDataSource, tương ứng,
// từ đó xây dựng danh sách sản phẩm phân trang sử dụng LivePagedListBuilder và lưu trữ chúng vào productPagedList hoặc laptopPagedList.
//
//Phương thức invalidate được sử dụng để hủy bỏ các tài nguyên được sử dụng bởi đối tượng ProductDataSource và
// LaptopDataSource. Nó sẽ kiểm tra nếu các đối tượng productDataSource và laptopDataSource đã được khởi tạo thì nó sẽ gọi phương thức invalidate() để giải phóng các tài nguyên được sử dụng.