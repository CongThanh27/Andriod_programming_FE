package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.DiaChiApiResponse;
import com.marwaeltayeb.souq.repository.DiaChiRepository;

public class DiaChiViewModel extends ViewModel {
    private final DiaChiRepository diaChiRepository;

    public DiaChiViewModel() {
        this.diaChiRepository = new DiaChiRepository();
    }
    public LiveData<DiaChiApiResponse> getDiaChis(int userId) {
        return diaChiRepository.getDiaChi(userId);
    }
}
