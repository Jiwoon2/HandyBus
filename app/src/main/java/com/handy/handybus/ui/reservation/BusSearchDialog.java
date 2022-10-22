package com.handy.handybus.ui.reservation;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

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
import com.handy.handybus.data.model.BusSearchItem;
import com.handy.handybus.databinding.DialogBusSearchBinding;
import com.handy.handybus.databinding.ItemBusSearchBinding;
import com.handy.handybus.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class BusSearchDialog extends DialogFragment {

    private DialogBusSearchBinding binding;
    private MainViewModel mainViewModel;

    private List<BusSearchItem> items;
    private RecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogBusSearchBinding.inflate(inflater, container, false);
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

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        items = mainViewModel.busSearchItems.getValue();

        adapter = new RecyclerViewAdapter();
        adapter.setOnItemClickListener(item -> {
            Bundle arguments = new Bundle();
            arguments.putString("busName", item.getName());
            arguments.putString("busRouteId", item.getRouteId());

            getParentFragmentManager().setFragmentResult("busRoute", arguments);
            dismiss();
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        binding.closeButton.setOnClickListener(v -> dismiss());

        binding.edit.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
                return true;
            }

            return false;
        });

        binding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString().trim();

                if (query.isEmpty()) {
                    adapter.submitList(null);
                    return;
                }

                ArrayList<BusSearchItem> filteredItems = new ArrayList<>();
                for (BusSearchItem item : items) {
                    if (item.getName().contains(query)) {
                        filteredItems.add(item);
                    }
                }

                adapter.submitList(filteredItems);
            }
        });

        binding.searchButton.setOnClickListener(v -> hideKeyboard());
    }

    public void onResume() {
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = (int) (((double) requireContext().getResources().getDisplayMetrics().heightPixels) * 0.7);
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }

    private void hideKeyboard() {
        try {
            InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(binding.edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignore) {
        }
    }


    private static class RecyclerViewAdapter extends ListAdapter<BusSearchItem, RecyclerViewAdapter.ItemViewHolder> {

        protected RecyclerViewAdapter() {
            super(new DiffUtil.ItemCallback<BusSearchItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull BusSearchItem oldItem, @NonNull BusSearchItem newItem) {
                    return TextUtils.equals(oldItem.getDocumentId(), newItem.getDocumentId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull BusSearchItem oldItem, @NonNull BusSearchItem newItem) {
                    return true;
                }

                @Override
                public Object getChangePayload(@NonNull BusSearchItem oldItem, @NonNull BusSearchItem newItem) {
                    return new Object();
                }
            });
        }

        private Consumer<BusSearchItem> onItemClickListener;

        public void setOnItemClickListener(Consumer<BusSearchItem> onItemClickListener) {
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
            BusSearchItem item = getItem(position);

            holder.binding.title.setText(item.getName());

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
