package com.example.subscriber.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.subscriber.R;
import com.example.subscriber.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    Boolean extended = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation
                    .findNavController(v)
                    .navigate(R.id.action_mainFragment2_to_newSubscriptionFragment);
        });

        return binding.getRoot();
    }
}
