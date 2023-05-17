package com.example.subscriber.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.subscriber.databinding.FragmentNewSubscriptionBinding;
import com.example.subscriber.ui.viewmodels.NewSubscriptionFragmentViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class NewSubscriptionFragment extends Fragment {
    FragmentNewSubscriptionBinding binding;
    NewSubscriptionFragmentViewModel viewModel;
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    Boolean dateChosen = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewSubscriptionBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NewSubscriptionFragmentViewModel.class);

        // Set up start state of button
        binding.confirmButton.shrink();

        // TextChangedListeners and OnClickListeners
        binding.nameTextFieldInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.name = binding.nameTextFieldInput.getText().toString();
                viewModel.changeExtend();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.everyTextFieldInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = binding.everyTextFieldInput.getText().toString();
                viewModel.every = Integer.valueOf(temp.isEmpty() ? "0" : temp);
                viewModel.changeExtend();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.costTextFieldInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = binding.costTextFieldInput.getText().toString();
                viewModel.price = Double.valueOf(temp.isEmpty() ? "0.0" : temp);
                viewModel.changeExtend();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.periodSpinnerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.period = binding.periodSpinnerInput.getText().toString();
                viewModel.changeExtend();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.currenciesSpinnerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.currency = binding.currenciesSpinnerInput.getText().toString();
                viewModel.changeExtend();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.descriptionTextFieldInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.description = binding.descriptionTextFieldInput.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.noteTextFieldInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.note = binding.noteTextFieldInput.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.dateButton.setOnClickListener(v -> {
            MaterialDatePicker datePicker = MaterialDatePicker
                    .Builder
                    .datePicker()
                    .setTitleText("SelectDate")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.show(getParentFragmentManager(), "First payment date picker");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat fromDatePicker = new SimpleDateFormat("dd MMM yyyy");
                SimpleDateFormat calendarToString = new SimpleDateFormat("dd.MM.yyyy");
                String date = datePicker.getHeaderText();
                try {
                    calendar.setTime(fromDatePicker.parse(date));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                binding.dateButton.setText(String.format(
                                "First payment date is: %s",
                                calendarToString.format(new Date(calendar.getTimeInMillis()))
                        )
                );

                viewModel.dateChanged = true;
                viewModel.date = calendar;
                viewModel.changeExtend();
            });
        });
        binding.confirmButton.setOnClickListener(v -> {
            if (viewModel.onAddSubscription(getContext())) {
                Navigation.findNavController(v).popBackStack();
            }

        });


        viewModel
                .shouldExtend
                .observe(getViewLifecycleOwner(), shouldExtend -> {
                            if (shouldExtend) {
                                binding.confirmButton.extend();
                            } else {
                                binding.confirmButton.shrink();
                            }
                        }
                );

        return binding.getRoot();
    }
}
