package com.example.subscriber.ui.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewSubscriptionFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> shouldExtend = new MutableLiveData<>(false);

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void changeExtend(Boolean extend) {
        shouldExtend.setValue(extend);
    }
}
