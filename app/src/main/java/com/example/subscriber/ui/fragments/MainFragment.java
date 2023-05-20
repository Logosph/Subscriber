package com.example.subscriber.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.subscriber.R;
import com.example.subscriber.data.db.SubscriptionDB;
import com.example.subscriber.data.db.SubscriptionDao;
import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.databinding.FragmentMainBinding;
import com.example.subscriber.ui.adapters.ListSubscribersAdapter;

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
        ListSubscribersAdapter adapter = new ListSubscribersAdapter(subs, getView());
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);
    }
}
