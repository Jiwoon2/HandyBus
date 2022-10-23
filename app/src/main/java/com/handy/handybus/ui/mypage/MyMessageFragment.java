package com.handy.handybus.ui.mypage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.data.model.Message;
import com.handy.handybus.databinding.FragmentMyMessageBinding;
import com.handy.handybus.databinding.ItemMyMessageBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyMessageFragment extends Fragment {

    private FragmentMyMessageBinding binding;
    private MyMessageViewModel viewModel;

    private final RecyclerViewAdapter adapter = new RecyclerViewAdapter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MyMessageViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        viewModel.messages.observe(getViewLifecycleOwner(), items -> adapter.submitList(items));
    }


    private static class RecyclerViewAdapter extends ListAdapter<Message, RecyclerViewAdapter.ItemViewHolder> {

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd. HH:mm", Locale.KOREA);
        private Consumer<Message> onItemClickListener;

        protected RecyclerViewAdapter() {
            super(new DiffUtil.ItemCallback<Message>() {
                @Override
                public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                    return TextUtils.equals(oldItem.getDocumentId(), newItem.getDocumentId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                    return TextUtils.equals(oldItem.getTitle(), newItem.getTitle()) &&
                            TextUtils.equals(oldItem.getMessage(), newItem.getMessage());
                }

                @Override
                public Object getChangePayload(@NonNull Message oldItem, @NonNull Message newItem) {
                    return new Object();
                }
            });
        }

        public void setOnItemClickListener(Consumer<Message> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemMyMessageBinding binding = ItemMyMessageBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Message item = getItem(position);
            ItemMyMessageBinding binding = holder.binding;

            if (TextUtils.isEmpty(item.getTitle())) {
                binding.tvBoardTitle.setVisibility(View.GONE);

            } else {
                binding.tvBoardTitle.setVisibility(View.VISIBLE);
                binding.tvBoardTitle.setText(item.getTitle());
            }

            binding.tvBoardContent.setText(item.getMessage());
            binding.tvBoardDate.setText(dateFormat.format(item.getTimestamp()));
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            public ItemMyMessageBinding binding;

            public ItemViewHolder(ItemMyMessageBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}