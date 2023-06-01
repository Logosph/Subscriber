package com.example.subscriber.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.data.repositories.SubscriptionDBRepository;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    public MutableLiveData<List<SubscriptionItem>> subs = new MutableLiveData<>();

    public void getAllFromDB(Context context, LifecycleOwner owner) {
        SubscriptionDBRepository.getAll(context);
        SubscriptionDBRepository.data.observe(owner, data -> subs.setValue(data));
    }


}
