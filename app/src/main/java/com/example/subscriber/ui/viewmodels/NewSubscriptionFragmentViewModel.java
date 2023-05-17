package com.example.subscriber.ui.viewmodels;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.data.repositories.SubscriptionDBRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

public class NewSubscriptionFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> shouldExtend = new MutableLiveData<>(false);
    public Boolean dateChanged = false;
    public String name = "";
    public String description = "";
    public String period = "";
    public Integer every = Integer.MIN_VALUE;
    public Double price = -1.0;
    public String currency = "";
    public String note = "";
    public Calendar date = Calendar.getInstance();
    public String serializedDate = "";

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void changeExtend() {
        shouldExtend.setValue(checkForFilled());
    }

    public Boolean onAddSubscription(Context context) {
        if (Boolean.TRUE.equals(shouldExtend.getValue())) {
            Gson gson = new GsonBuilder().create();
            serializedDate = gson.toJson(date);
            SubscriptionDBRepository.insert(context, new SubscriptionItem(
                    name,
                    description,
                    every,
                    period,
                    price,
                    currency,
                    serializedDate
            ));
            return true;
        }
        return false;
    }

    private Boolean checkForFilled() {
        return !name.isEmpty() && price >= 0.0 && every > 0 && !period.isEmpty() && !currency.isEmpty() && dateChanged;
    }
}
