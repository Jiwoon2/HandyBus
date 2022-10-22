package com.handy.handybus.ui.main.children;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ReservationViewModelProvider implements ViewModelProvider.Factory {

    final Context context;

    public ReservationViewModelProvider(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ReservationViewModel(context);
    }
}
