package com.example.subscriber.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.subscriber.R;
import com.example.subscriber.data.db.SubscriptionDB;
import com.example.subscriber.data.db.SubscriptionDao;
import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.databinding.FragmentMainBinding;
import com.example.subscriber.ui.adapters.ListSubscribersAdapter;
import com.example.subscriber.ui.viewmodels.MainFragmentViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    MainFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation
                    .findNavController(v)
                    .navigate(R.id.action_mainFragment2_to_newSubscriptionFragment);
        });

        viewModel
                .subs
                .observe(getViewLifecycleOwner(), subs -> {
                    ListSubscribersAdapter adapter = new ListSubscribersAdapter(subs, getView());
                    binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.recycler.setAdapter(adapter);
        });

        viewModel.getAllFromDB(getContext(), getViewLifecycleOwner());

        return binding.getRoot();
    }
}

