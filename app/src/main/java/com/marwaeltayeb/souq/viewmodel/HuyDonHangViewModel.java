package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.marwaeltayeb.souq.repository.HuydonhangRepository;

import okhttp3.ResponseBody;

public class HuyDonHangViewModel extends ViewModel {
    private final HuydonhangRepository huydonhangRepository;

    public HuyDonHangViewModel() {this.huydonhangRepository = new HuydonhangRepository();}
    public LiveData<ResponseBody> getHuyDonHang(String order_number) {
        return huydonhangRepository.HuyDonHang(order_number);
    }
}
