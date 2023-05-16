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

        binding.confirmButton.shrink();

        addTextChangedListener(binding.nameTextFieldInput);
        addTextChangedListener(binding.everyTextFieldInput);
        addTextChangedListener(binding.costTextFieldInput);
        addTextChangedListener(binding.periodSpinnerInput);
        addTextChangedListener(binding.currenciesSpinnerInput);

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
                dateChosen = true;
                viewModel.changeExtend(checkForFilled());
            });
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

    private Boolean checkForFilled() {
        return
                !binding.nameTextFieldInput.getText().toString().isEmpty() &&
                        !binding.everyTextFieldInput.getText().toString().isEmpty() &&
                        !binding.costTextFieldInput.getText().toString().isEmpty() &&
                        !binding.periodSpinnerInput.getText().toString().isEmpty() &&
                        !binding.currenciesSpinnerInput.getText().toString().isEmpty() &&
                        dateChosen;
    }

    private void addTextChangedListener(com.google.android.material.textfield.TextInputEditText view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.changeExtend(checkForFilled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void addTextChangedListener(android.widget.AutoCompleteTextView view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.changeExtend(checkForFilled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}
