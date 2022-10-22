package com.handy.handybus.ui.main.children;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.R;
import com.handy.handybus.data.model.UserReserv_Item;
import com.handy.handybus.databinding.FragmentConfirmreservationBinding;
import com.handy.handybus.databinding.ItemReservationBinding;


public class ConfirmReservationFragment extends Fragment {

    private FragmentConfirmreservationBinding binding;
    private ConfirmReservationViewModel viewModel;

    private final RecyclerViewAdapter adapter = new RecyclerViewAdapter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfirmreservationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ConfirmReservationViewModel.class);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onEditClicked(UserReserv_Item item) {
                if (getParentFragmentManager().findFragmentByTag("ReservationEditor") == null) {
                    getParentFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, ReservationFragment.getInstance(item), "ReservationEditor")
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onRemoveClicked(UserReserv_Item item) {
                new AlertDialog.Builder(requireContext(), R.style.RoundedCornersDialog)
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("확인", (dialogInterface, i) -> {
                            viewModel.removeReservation(item);

                            Toast.makeText(requireContext().getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

        binding.recyclerView.setAdapter(adapter);

        viewModel.reservations.observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);

            binding.emptyView.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }


    private static class RecyclerViewAdapter extends ListAdapter<UserReserv_Item, RecyclerViewAdapter.ItemViewHolder> {

        public interface OnItemClickListener {
            public void onEditClicked(UserReserv_Item item);

            public void onRemoveClicked(UserReserv_Item item);
        }


        private OnItemClickListener onItemClickListener;

        protected RecyclerViewAdapter() {
            super(new DiffUtil.ItemCallback<UserReserv_Item>() {
                @Override
                public boolean areItemsTheSame(@NonNull UserReserv_Item oldItem, @NonNull UserReserv_Item newItem) {
                    return TextUtils.equals(oldItem.getKey(), newItem.getKey());
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserReserv_Item oldItem, @NonNull UserReserv_Item newItem) {
                    return TextUtils.equals(oldItem.getResArrivals(), newItem.getResArrivals()) &&
                            TextUtils.equals(oldItem.getResBusNum(), newItem.getResBusNum()) &&
                            TextUtils.equals(oldItem.getResBusRouteId(), newItem.getResBusRouteId()) &&
                            TextUtils.equals(oldItem.getResDate(), newItem.getResDate()) &&
                            TextUtils.equals(oldItem.getResDepartures(), newItem.getResDepartures()) &&
                            TextUtils.equals(oldItem.getResHelp(), newItem.getResHelp()) &&
                            TextUtils.equals(oldItem.getResNum(), newItem.getResNum()) &&
                            TextUtils.equals(oldItem.getResWheel(), newItem.getResWheel());
                }

                @Override
                public Object getChangePayload(@NonNull UserReserv_Item oldItem, @NonNull UserReserv_Item newItem) {
                    return new Object();
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemReservationBinding binding = ItemReservationBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            UserReserv_Item item = getItem(position);
            ItemReservationBinding binding = holder.binding;

            binding.tvIsReservation.setText("예약 정보");

            binding.tvDepartStation.setText(item.getResDepartures());
            binding.tvArrStation.setText(item.getResArrivals());
            binding.tvGetonBusNm.setText(item.getResBusNum());

            binding.tvPersonNm.setText("성인 " + item.getResNum() + "명");
            binding.tvWheel.setText(item.getResWheel());
            binding.tvHelp.setText(item.getResHelp());

            binding.tvReservDate.setText(item.getResDate() + " 예약");
            binding.tvReservDepart.setText(item.getResDepartures());
            binding.tvReservArrival.setText(item.getResArrivals());

            binding.overflowButton.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.reservation_overflow_menu);
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (onItemClickListener != null) {
                        int itemId = menuItem.getItemId();

                        if (itemId == R.id.action_edit) {
                            onItemClickListener.onEditClicked(item);

                        } else if (itemId == R.id.action_remove) {
                            onItemClickListener.onRemoveClicked(item);
                        }
                    }

                    return true;
                });

                popupMenu.show();
            });
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            public ItemReservationBinding binding;

            public ItemViewHolder(ItemReservationBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}