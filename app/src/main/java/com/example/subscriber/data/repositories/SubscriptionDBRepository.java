package com.example.subscriber.data.repositories;

import android.content.Context;

import com.example.subscriber.data.db.SubscriptionDB;
import com.example.subscriber.data.db.SubscriptionDao;
import com.example.subscriber.data.db.SubscriptionItem;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubscriptionDBRepository {
    static SubscriptionDB subscriptionDB;
    static SubscriptionDao subscriptionDao;

    public static void insert(Context context, SubscriptionItem subscriptionItem) {
        subscriptionDB = SubscriptionDB.getInstance(context);
        subscriptionDao = subscriptionDB.subscriptionDao();

        subscriptionDao
                .insert(subscriptionItem)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {});
    }
}
