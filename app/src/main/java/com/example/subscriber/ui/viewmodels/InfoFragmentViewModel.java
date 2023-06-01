package com.example.subscriber.ui.viewmodels;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.subscriber.R;
import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.data.repositories.SubscriptionDBRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InfoFragmentViewModel extends ViewModel {

    public SubscriptionItem subscriptionItem;
    Gson gson = new GsonBuilder().create();

    public void setSubscriptionItem(String json) {
        subscriptionItem = gson.fromJson(json, SubscriptionItem.class);
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Calendar calendar = gson.fromJson(subscriptionItem.getDate(), Calendar.class);
        Date date_ob = new Date(calendar.getTimeInMillis());

        return sdf.format(date_ob);
    }

    public void callSnackbar(View v, Context context){
        Snackbar.make(v, R.string.are_you_sure, Snackbar.LENGTH_SHORT).setAction(R.string.delete, v1 -> {
            SubscriptionDBRepository.delete(context, subscriptionItem.getId());
            Navigation.findNavController(v).popBackStack();
        }).show();
    }

    public Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("Subscription", gson.toJson(subscriptionItem));

        return bundle;
    }
}
