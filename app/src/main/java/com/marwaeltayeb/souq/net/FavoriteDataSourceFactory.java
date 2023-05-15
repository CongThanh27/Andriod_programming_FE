package com.marwaeltayeb.souq.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;

public class FavoriteDataSourceFactory  extends DataSource.Factory{
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> favoriteLiveDataSource = new MutableLiveData<>();
    public static FavoriteDataSource favoriteDataSource;
    private final int userId;

    public FavoriteDataSourceFactory(int userId) {
        this.userId = userId;
    }

    @Override
    public DataSource create() {
        favoriteDataSource= new FavoriteDataSource(userId);
        favoriteLiveDataSource.postValue(favoriteDataSource);
        return favoriteDataSource;
    }
}
