package com.example.subscriber.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface SubscriptionDao {

    @Insert
    Completable insert(SubscriptionItem subscriptionItem);

    @Query("SELECT * FROM subscription_table")
    Observable<List<SubscriptionItem>> getAllSubs();

    @Query("SELECT * FROM subscription_table WHERE id = :id")
    Observable<SubscriptionItem> getSubById(int id);
}
