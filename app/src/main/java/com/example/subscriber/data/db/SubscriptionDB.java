package com.example.subscriber.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SubscriptionItem.class}, version = 1)
public abstract class SubscriptionDB extends RoomDatabase {
    private static SubscriptionDB instance;

    public abstract SubscriptionDao subscriptionDao();

    public static synchronized SubscriptionDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            SubscriptionDB.class, "subscription_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
