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
import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.data.repositories.SubscriptionDBRepository;
import com.example.subscriber.databinding.FragmentInfoBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InfoFragment extends Fragment {

    FragmentInfoBinding binding;
    SubscriptionItem subscriptionItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);

        Gson gson = new GsonBuilder().create();
        subscriptionItem = gson.fromJson(getArguments().getString("Subscription"), SubscriptionItem.class);

        binding.description.setText(subscriptionItem.getDescription());
        binding.headerTitle.setText(subscriptionItem.getName());
        binding.note.setText(subscriptionItem.getNote());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Calendar calendar = gson.fromJson(subscriptionItem.getDate(), Calendar.class);
        Date date_ob = new Date(calendar.getTimeInMillis());
        String date = sdf.format(date_ob);
        binding.firstPaymentDisplay.setText(date);
        binding.priceDisplay.setText(MessageFormat.format("{0} {1}", subscriptionItem.getPrice(), subscriptionItem.getCurrency()));
        binding.everyDisplay.setText(MessageFormat.format("{0} {1}", subscriptionItem.getEvery(), subscriptionItem.getPeriod()));

        binding.deleteButton.setOnClickListener(v -> {
            Snackbar.make(v, R.string.are_you_sure, Snackbar.LENGTH_SHORT).setAction(R.string.delete, v1 -> {
                SubscriptionDBRepository.delete(getContext(), subscriptionItem.getId());
                Navigation.findNavController(v).popBackStack();
            }).show();
        });

        binding.editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("Subscription", gson.toJson(subscriptionItem));
            Navigation.findNavController(v).navigate(R.id.action_infoFragment_to_newSubscriptionFragment, bundle);
        });

        return binding.getRoot();
    }
}
