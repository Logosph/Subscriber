package com.example.subscriber.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.subscriber.R;
import com.example.subscriber.data.db.SubscriptionDB;
import com.example.subscriber.data.db.SubscriptionDao;
import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.databinding.FragmentMainBinding;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    Boolean extended = false;
    List<SubscriptionItem> db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation
                    .findNavController(v)
                    .navigate(R.id.action_mainFragment2_to_newSubscriptionFragment);
        });


        SubscriptionDB subscriptionDB = SubscriptionDB.getInstance(getContext());
        SubscriptionDao subscriptionDao = subscriptionDB.subscriptionDao();
        Disposable subsDisposable = subscriptionDao
                .getAllSubs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoaded);

        return binding.getRoot();
    }

    private void onLoaded(List<SubscriptionItem> subs) {
        for (int i = 0; i < subs.size(); i++) {
            Log.d("Database", subs.get(i).toString());
        }
    }
}
