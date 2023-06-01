package com.example.subscriber.ui.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscriber.R;
import com.example.subscriber.data.db.SubscriptionItem;
import com.example.subscriber.databinding.SubscribesItemBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

public class ListSubscribersAdapter extends RecyclerView.Adapter<ListSubscribersAdapter.SubscribeViewHolder> {

    List<SubscriptionItem> data;
    View view;

    public ListSubscribersAdapter(List<SubscriptionItem> data, View view) {
        this.data = data;
        this.view = view;
    }

    @NonNull
    @Override
    public SubscribeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubscribesItemBinding binding = SubscribesItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new SubscribeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeViewHolder holder, int position) {
        Calendar current = Calendar.getInstance();
        Calendar target;
        Gson gson = new GsonBuilder().create();
        target = gson.fromJson(data.get(position).getDate(), Calendar.class);

        holder.binding.card.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            String object = gson.toJson(data.get(position));
            bundle.putString("Subscription", object);
            Navigation.findNavController(view).navigate(R.id.action_mainFragment2_to_infoFragment, bundle);
        });

        holder.binding.name.setText(data.get(position).getName());
        holder.binding.description.setText(data.get(position).getDescription());
        holder.binding.every.setText(MessageFormat.format("{0} {1}", data.get(position).getEvery(), data.get(position).getPeriod()));
        holder.binding.price.setText(MessageFormat.format("{0} {1}", data.get(position).getPrice(), data.get(position).getCurrency()));

        String period = data.get(position).getPeriod();
        Integer left = 0;
        String days = "";
        if (period.equals("Day") || period.equals("День")) {
            while (target.before(current)) {
                target.add(Calendar.DAY_OF_MONTH, 1);
                left++;
                left %= data.get(position).getEvery();
            }
            left = data.get(position).getEvery() - left;
        } else if (period.equals("Week") || period.equals("Неделя")) {
            while (target.before(current)) {
                target.add(Calendar.DAY_OF_MONTH, 1);
                left++;
                if (left % 7 == 0 && left / 7 == data.get(position).getEvery()) {
                    left = 0;
                }
            }
            left = data.get(position).getEvery() * 7 - left;
        } else if (period.equals("Month") || period.equals("Месяц")) {
            Integer months = 0;
            while (target.before(current)) {
                target.add(Calendar.DAY_OF_MONTH, 1);
                left++;
                if (target.get(Calendar.DAY_OF_MONTH) == gson.fromJson(data.get(position).getDate(), Calendar.class).get(Calendar.DAY_OF_MONTH)) {
                    months++;
                    if (months == data.get(position).getEvery()) {
                        left = 0;
                        months = 0;
                    }
                }
            }
            left = 0;
            while (months != data.get(position).getEvery()) {
                    left++;
                    target.add(Calendar.DAY_OF_MONTH, 1);
                if (target.get(Calendar.DAY_OF_MONTH) == gson.fromJson(data.get(position).getDate(), Calendar.class).get(Calendar.DAY_OF_MONTH)) {
                    months++;
                }
            }
        } else if (period.equals("Year") || period.equals("Год")) {
            Integer years = 0;
            while (target.before(current)) {
                target.add(Calendar.DAY_OF_MONTH, 1);
                left++;
                if (target.get(Calendar.DAY_OF_YEAR) == gson.fromJson(data.get(position).getDate(), Calendar.class).get(Calendar.DAY_OF_YEAR)) {
                    years++;
                    if (years == data.get(position).getEvery()) {
                        left = 0;
                        years = 0;
                    }
                }
            }
            left = 0;
            while (years != data.get(position).getEvery()) {
                left++;
                target.add(Calendar.DAY_OF_MONTH, 1);
                if (target.get(Calendar.DAY_OF_YEAR) == gson.fromJson(data.get(position).getDate(), Calendar.class).get(Calendar.DAY_OF_YEAR)) {
                    years++;
                }
            }
        }

        if (period.equals("Year") || period.equals("Month") || period.equals("Week")) {
            holder.binding.left.setText(MessageFormat.format("Payment in {0} days", left));
        } else {
            if (left % 10 == 1 && left % 100 != 11) {
                days = " день";
            } else if (1 < left % 10 && left % 10 < 5 && (left % 100 > 14 || left % 100 < 11)) {
                days = " дня";
            } else {
                days = " дней";
            }
            holder.binding.left.setText(MessageFormat.format("Через {0} {1}", left, days));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class SubscribeViewHolder extends RecyclerView.ViewHolder {

        SubscribesItemBinding binding;

        public SubscribeViewHolder(@NonNull SubscribesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
