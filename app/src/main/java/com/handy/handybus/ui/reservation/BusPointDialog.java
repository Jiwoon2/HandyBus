package com.handy.handybus.ui.reservation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.R;
import com.handy.handybus.data.model.StationRoute_Item;
import com.handy.handybus.databinding.DialogPointBinding;
import com.handy.handybus.databinding.ItemBusSearchBinding;
import com.handy.handybus.ui.main.children.ReservationViewModel;

import java.util.List;

public class BusPointDialog extends DialogFragment {

    public static BusPointDialog getInstance(boolean isDeparture) {
        Bundle arguments = new Bundle();
        arguments.putBoolean("isDeparture", isDeparture);

        BusPointDialog dialog = new BusPointDialog();
        dialog.setArguments(arguments);

        return dialog;
    }

    private boolean isDeparture = true;

    private DialogPointBinding binding;
    private ReservationViewModel reservationViewModel;

    private List<StationRoute_Item> items;
    private RecyclerViewAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            isDeparture = getArguments().getBoolean("isDeparture", true);
        }

        if (savedInstanceState != null) {
            savedInstanceState.getBoolean("isDeparture", true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isDeparture", isDeparture);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogPointBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public int getTheme() {
        return R.style.RoundedCornersDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reservationViewModel = new ViewModelProvider(getParentFragment()).get(ReservationViewModel.class);
        items = reservationViewModel.routeItems.getValue();

        if (!isDeparture) {
            binding.title.setText("도착지 선택");
        }

        adapter = new RecyclerViewAdapter();
        adapter.setOnItemClickListener(item -> {
            Bundle arguments = new Bundle();
            arguments.putString("stationName", item.getStationNm());

            getParentFragmentManager().setFragmentResult(isDeparture ? "departurePoint" : "arrivalPoint", arguments);
            dismiss();
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        binding.closeButton.setOnClickListener(v -> dismiss());

        adapter.submitList(items);
    }

    public void onResume() {
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = (int) (((double) requireContext().getResources().getDisplayMetrics().heightPixels) * 0.7);
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }


    private static class RecyclerViewAdapter extends ListAdapter<StationRoute_Item, RecyclerViewAdapter.ItemViewHolder> {

        protected RecyclerViewAdapter() {
            super(new DiffUtil.ItemCallback<StationRoute_Item>() {
                @Override
                public boolean areItemsTheSame(@NonNull StationRoute_Item oldItem, @NonNull StationRoute_Item newItem) {
                    return TextUtils.equals(oldItem.getStationNm(), newItem.getStationNm());
                }

                @Override
                public boolean areContentsTheSame(@NonNull StationRoute_Item oldItem, @NonNull StationRoute_Item newItem) {
                    return true;
                }

                @Override
                public Object getChangePayload(@NonNull StationRoute_Item oldItem, @NonNull StationRoute_Item newItem) {
                    return new Object();
                }
            });
        }

        private Consumer<StationRoute_Item> onItemClickListener;

        public void setOnItemClickListener(Consumer<StationRoute_Item> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemBusSearchBinding binding = ItemBusSearchBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            StationRoute_Item item = getItem(position);

            holder.binding.title.setText(item.getStationNm());

            holder.binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.accept(item);
                }
            });
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            public ItemBusSearchBinding binding;

            public ItemViewHolder(ItemBusSearchBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
