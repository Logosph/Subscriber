package com.example.subscriber.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.subscriber.data.db.SubscriptionDB;
import com.example.subscriber.data.db.SubscriptionDao;
import com.example.subscriber.data.db.SubscriptionItem;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubscriptionDBRepository {
    static SubscriptionDB subscriptionDB;
    static SubscriptionDao subscriptionDao;
    public static MutableLiveData<List<SubscriptionItem>> data = new MutableLiveData<>();

    public static void getAll(Context context) {
        subscriptionDB = SubscriptionDB.getInstance(context);
        subscriptionDao = subscriptionDB.subscriptionDao();


        subscriptionDao
                .getAllSubs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SubscriptionItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<SubscriptionItem> subscriptionItems) {
                        data.setValue(subscriptionItems);
                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void insert(Context context, SubscriptionItem subscriptionItem) {
        subscriptionDB = SubscriptionDB.getInstance(context);
        subscriptionDao = subscriptionDB.subscriptionDao();

        subscriptionDao
                .insert(subscriptionItem)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {});
    }

    public static void delete(Context context, Integer id) {
        subscriptionDB = SubscriptionDB.getInstance(context);
        subscriptionDao = subscriptionDB.subscriptionDao();

        subscriptionDao
                .delete(id)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {});
    }

    public static void update(Context context, SubscriptionItem subscriptionItem) {
        subscriptionDB = SubscriptionDB.getInstance(context);
        subscriptionDao = subscriptionDB.subscriptionDao();

        subscriptionDao
                .update(subscriptionItem)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {});
    }
}
