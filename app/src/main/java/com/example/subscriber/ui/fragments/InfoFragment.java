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

import com.example.subscriber.R;
import com.example.subscriber.databinding.FragmentInfoBinding;
import com.example.subscriber.ui.viewmodels.InfoFragmentViewModel;

import java.text.MessageFormat;

public class InfoFragment extends Fragment {

    FragmentInfoBinding binding;
    InfoFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InfoFragmentViewModel.class);
        viewModel.setSubscriptionItem(getArguments().getString("Subscription"));

        binding.description.setText(viewModel.subscriptionItem.getDescription());
        binding.headerTitle.setText(viewModel.subscriptionItem.getName());
        binding.note.setText(viewModel.subscriptionItem.getNote());
        binding.firstPaymentDisplay.setText(viewModel.getFormattedDate());
        binding.priceDisplay.setText(MessageFormat.format("{0} {1}", viewModel.subscriptionItem.getPrice(), viewModel.subscriptionItem.getCurrency()));
        binding.everyDisplay.setText(MessageFormat.format("{0} {1}", viewModel.subscriptionItem.getEvery(), viewModel.subscriptionItem.getPeriod()));

        binding.deleteButton.setOnClickListener(v -> {
            viewModel.callSnackbar(v, getContext());
        });

        binding.editButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_infoFragment_to_newSubscriptionFragment, viewModel.createBundle());
        });

        return binding.getRoot();
    }
}
