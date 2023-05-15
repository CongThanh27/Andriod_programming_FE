package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.repository.UpdateInfoUserRepository;

import okhttp3.ResponseBody;

public class UpdateInfoUserViewModel extends ViewModel {
    private final UpdateInfoUserRepository updateInfoUserRepository;

    public UpdateInfoUserViewModel() {
        this.updateInfoUserRepository = new UpdateInfoUserRepository();
    }
    public LiveData<ResponseBody> UpdateInfoUsers(int id, String phone_number, String address) {
        return updateInfoUserRepository.UpdateInfoUser(id,phone_number,address);
    }
}

