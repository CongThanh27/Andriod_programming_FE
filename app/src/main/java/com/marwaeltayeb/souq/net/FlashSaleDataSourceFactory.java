package com.marwaeltayeb.souq.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;


public class FlashSaleDataSourceFactory extends DataSource.Factory{
    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> flashSaleLiveDataSource = new MutableLiveData<>();
    public static FlashSaleDataSource flashSaleDataSource;
    private final int userId;

    public FlashSaleDataSourceFactory(int userId) {
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        flashSaleDataSource = new FlashSaleDataSource(userId);

        // Posting the Data source to get the values
        flashSaleLiveDataSource.postValue(flashSaleDataSource);

        // Returning the Data source
        return flashSaleDataSource;
    }
}
