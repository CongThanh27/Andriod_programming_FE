package com.marwaeltayeb.souq.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.ProductInOnderRespone;
import com.marwaeltayeb.souq.repository.ProductInOrtherRepository;

public class ProductInOrderViewModel extends ViewModel {
    private final ProductInOrtherRepository  productInOrtherRepository;

    public ProductInOrderViewModel() {
        productInOrtherRepository = new ProductInOrtherRepository();
    }

    public LiveData<ProductInOnderRespone> getProductsInOrder(int userId, String ordernumber) {
        return productInOrtherRepository.getProductInOrders(userId, ordernumber);
    }

}
