package com.handy.handybus.ui.board;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.handy.handybus.data.model.Board;
import com.handy.handybus.data.model.Message;
import com.handy.handybus.databinding.FragmentPostDetailBinding;
import com.handy.handybus.databinding.ItemBoardDetailHeaderBinding;
import com.handy.handybus.databinding.ItemBoardDetailReplyBinding;
import com.handy.handybus.databinding.ItemBoardDetailRereplyBinding;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PostDetailFragment extends Fragment {

    public static PostDetailFragment getInstance(Board board) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("board", board);

        PostDetailFragment fragment = new PostDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    private Board board;
    private FragmentPostDetailBinding binding;
    private PostDetailViewModel viewModel;
    private BoardAdapter boardAdapter;
    private final MessageAdapter messageAdapter = new MessageAdapter();

    private Message parentMessage;

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (messageAdapter.isReReplyMode()) {
                messageAdapter.clearReReplyMode();

            } else {
                callback.setEnabled(false);
                requireActivity().onBackPressed();
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            board = getArguments().getParcelable("board");
        }

        if (savedInstanceState != null) {
            board = savedInstanceState.getParcelable("board");
        }

        assert board != null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("board", board);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, new PostDetailViewModelProvider(board)).get(PostDetailViewModel.class);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        boardAdapter = new BoardAdapter(viewModel.getMyUid());
        boardAdapter.setOnParticipateClickListener((ignore) -> viewModel.toggleParticipation());

        messageAdapter.setOnReReplyButtonClickListener((parentMessage) -> {
            this.parentMessage = parentMessage;
        });

        ConcatAdapter adapter = new ConcatAdapter(Arrays.asList(boardAdapter, messageAdapter));
        binding.recyclerView.setAdapter(adapter);

        viewModel.board.observe(getViewLifecycleOwner(), board -> {
            if (board == null) {
                requireActivity().onBackPressed();
                return;
            }

            boardAdapter.submitList(Arrays.asList(board));
        });

        viewModel.message.observe(getViewLifecycleOwner(), messageAdapter::submitList);

        binding.sendButton.setOnClickListener(v -> {
            String message = binding.replyEditText.getText().toString().trim();
            if (message.isEmpty()) return;

            viewModel.sendMessage(parentMessage, message);
            binding.replyEditText.setText("");

            messageAdapter.clearReReplyMode();
        });
    }

    private static class BoardAdapter extends ListAdapter<Board, BoardAdapter.ItemViewHolder> {

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd. HH:mm", Locale.KOREA);
        private final String myUid;

        private Consumer<Void> onParticipateClickListener;

        public BoardAdapter(String myUid) {
            super(new DiffUtil.ItemCallback<Board>() {
                @Override
                public boolean areItemsTheSame(@NonNull Board oldItem, @NonNull Board newItem) {
                    return TextUtils.equals(oldItem.getDocumentId(), newItem.getDocumentId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Board oldItem, @NonNull Board newItem) {
                    if (oldItem.getParticipants().size() != newItem.getParticipants().size()) {
                        return false;
                    }

                    for (String participant : oldItem.getParticipants()) {
                        if (!newItem.getParticipants().contains(participant)) {
                            return false;
                        }
                    }

                    return TextUtils.equals(oldItem.getTitle(), newItem.getTitle()) &&
                            TextUtils.equals(oldItem.getMessage(), newItem.getMessage()) &&
                            TextUtils.equals(oldItem.getName(), newItem.getName());
                }

                @Override
                public Object getChangePayload(@NonNull Board oldItem, @NonNull Board newItem) {
                    return new Object();
                }
            });

            this.myUid = myUid;
        }

        public void setOnParticipateClickListener(Consumer<Void> onParticipateClickListener) {
            this.onParticipateClickListener = onParticipateClickListener;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemBoardDetailHeaderBinding binding = ItemBoardDetailHeaderBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Board item = getItem(position);
            ItemBoardDetailHeaderBinding binding = holder.binding;

            binding.tvBoardNickName.setText(item.getName());
            binding.tvBoardJoinCnt.setText(String.valueOf(item.getParticipants().size()));
            binding.tvBoardTitle.setText(item.getTitle());
            binding.tvBoardContent.setText(item.getMessage());
            binding.tvBoardDate.setText(dateFormat.format(item.getTimestamp()));

            if (item.getParticipants().contains(myUid)) {
                binding.participateButton.setText("참여중");

                // 글 작성자는 무조건 참여
                binding.participateButton.setEnabled(!TextUtils.equals(item.getUid(), myUid));
            }

            binding.participateButton.setOnClickListener(v -> {
                if (onParticipateClickListener != null) {
                    onParticipateClickListener.accept(null);
                }
            });
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            public ItemBoardDetailHeaderBinding binding;

            public ItemViewHolder(ItemBoardDetailHeaderBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    private static class MessageAdapter extends ListAdapter<Message, RecyclerView.ViewHolder> {

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd. HH:mm", Locale.KOREA);
        private String parentMessageDocumentId;
        private Consumer<Message> onReReplyButtonClickListener;

        public MessageAdapter() {
            super(new DiffUtil.ItemCallback<Message>() {
                @Override
                public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                    return TextUtils.equals(oldItem.getDocumentId(), newItem.getDocumentId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                    return TextUtils.equals(oldItem.getTitle(), newItem.getTitle()) &&
                            TextUtils.equals(oldItem.getMessage(), newItem.getMessage()) &&
                            TextUtils.equals(oldItem.getName(), newItem.getName());
                }

                @Override
                public Object getChangePayload(@NonNull Message oldItem, @NonNull Message newItem) {
                    return new Object();
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            double id = getItem(position).getId();

            if (id - ((int) id) == 0) { // Reply
                return 0;
            } else { // ReReply
                return 1;
            }
        }

        public void setOnReReplyButtonClickListener(Consumer<Message> onReReplyButtonClickListener) {
            this.onReReplyButtonClickListener = onReReplyButtonClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (viewType == 0) {
                ItemBoardDetailReplyBinding binding = ItemBoardDetailReplyBinding.inflate(inflater, parent, false);
                return new ReplyItemViewHolder(binding);

            } else {
                ItemBoardDetailRereplyBinding binding = ItemBoardDetailRereplyBinding.inflate(inflater, parent, false);
                return new ReReplyItemViewHolder(binding);
            }
        }

        public void clearReReplyMode() {
            String parentMessageDocumentId = this.parentMessageDocumentId;
            if (parentMessageDocumentId == null) return;

            try {
                int index = -1;

                for (int i = 0; i < getItemCount(); i++) {
                    if (TextUtils.equals(getItem(i).getDocumentId(), parentMessageDocumentId)) {
                        index = i;
                        break;
                    }
                }

                this.parentMessageDocumentId = null;

                if (index >= 0) {
                    notifyItemChanged(index, new Object());
                }
            } catch (Exception ignore) {
                this.parentMessageDocumentId = null;
                notifyDataSetChanged();
            }

            if (onReReplyButtonClickListener != null) {
                onReReplyButtonClickListener.accept(null);
            }
        }

        public boolean isReReplyMode() {
            return parentMessageDocumentId != null;
        }

        @Override
        public void submitList(@Nullable List<Message> list) {
            submitList(list, null);
        }

        @Override
        public void submitList(@Nullable List<Message> list, @Nullable Runnable commitCallback) {
            if (parentMessageDocumentId != null && list != null) {
                boolean hasParentMessage = false;

                for (Message message : list) {
                    if (TextUtils.equals(message.getDocumentId(), parentMessageDocumentId)) {
                        hasParentMessage = true;
                        break;
                    }
                }

                if (!hasParentMessage) {
                    clearReReplyMode();
                }
            }

            super.submitList(list, commitCallback);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Message item = getItem(position);

            if (holder instanceof ReplyItemViewHolder) {
                ItemBoardDetailReplyBinding binding = ((ReplyItemViewHolder) holder).binding;

                if (this.parentMessageDocumentId != null) {
                    if (TextUtils.equals(item.getDocumentId(), parentMessageDocumentId)) {
                        binding.reReplyModeIndicator.setVisibility(View.VISIBLE);

                    } else {
                        binding.reReplyModeIndicator.setVisibility(View.GONE);
                    }
                } else {
                    binding.reReplyModeIndicator.setVisibility(View.GONE);
                }

                binding.tvBoardNickName.setText(item.getName());
                binding.tvBoardContent.setText(item.getMessage());
                binding.tvBoardDate.setText(dateFormat.format(item.getTimestamp()));

                binding.replyButton.setOnClickListener(v -> {
                    this.parentMessageDocumentId = item.getDocumentId();
                    binding.reReplyModeIndicator.setVisibility(View.VISIBLE);

                    if (onReReplyButtonClickListener != null) {
                        onReReplyButtonClickListener.accept(item);
                    }
                });
            } else if (holder instanceof ReReplyItemViewHolder) {
                ItemBoardDetailRereplyBinding binding = ((ReReplyItemViewHolder) holder).binding;

                binding.tvBoardNickName.setText(item.getName());
                binding.tvBoardContent.setText(item.getMessage());
                binding.tvBoardDate.setText(dateFormat.format(item.getTimestamp()));
            }
        }

        static class ReplyItemViewHolder extends RecyclerView.ViewHolder {
            public ItemBoardDetailReplyBinding binding;

            public ReplyItemViewHolder(ItemBoardDetailReplyBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        static class ReReplyItemViewHolder extends RecyclerView.ViewHolder {
            public ItemBoardDetailRereplyBinding binding;

            public ReReplyItemViewHolder(ItemBoardDetailRereplyBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
