package com.marwaeltayeb.souq.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;

public class SoldDataSourceFactory extends DataSource.Factory{
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> soldLiveDataSource = new MutableLiveData<>();
    public static SoldDataSource soldDataSource;

    private final int userId;

    public SoldDataSourceFactory(int userId) {
        this.userId = userId;
    }

    @Override
    public DataSource create() {
        soldDataSource = new SoldDataSource(userId);
        soldLiveDataSource.postValue(soldDataSource);
        return soldDataSource;
    }
}
