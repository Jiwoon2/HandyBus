package com.handy.handybus.ui.main.children;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.handy.handybus.R;
import com.handy.handybus.data.model.Board;
import com.handy.handybus.data.model.Profile;
import com.handy.handybus.databinding.FragmentWriteBoardBinding;
import com.handy.handybus.databinding.ItemBoardlistBinding;
import com.handy.handybus.ui.board.PostDetailFragment;
import com.handy.handybus.ui.board.PostFragment;
import com.handy.handybus.ui.main.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

public class WriteBoardFragment extends Fragment {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    private FragmentWriteBoardBinding binding;
    private WriteBoardViewModel viewModel;

    private MainViewModel mainViewModel;
    private Profile profile = null;

    private RecyclerViewAdapter adapter = new RecyclerViewAdapter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.profile.observe(getViewLifecycleOwner(), (profile) -> {
            if (profile == null) return;
            this.profile = profile;
        });

        binding = FragmentWriteBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WriteBoardViewModel.class);

        adapter.setOnItemClickListener(item -> {
            if (getParentFragmentManager().findFragmentByTag("PostDetail") == null) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, PostDetailFragment.getInstance(item), "PostDetail")
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        binding.writeBtn.setOnClickListener(v -> {

            if ( profile != null && profile.getBanned() != 0 ) {
                Toast.makeText(requireActivity().getApplication(),
                        "글쓰기가 차단 되었습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getParentFragmentManager().findFragmentByTag("Post") == null) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new PostFragment(), "Post")
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewModel.boards.observe(getViewLifecycleOwner(), items -> adapter.submitList(items));
    }


    private static class RecyclerViewAdapter extends ListAdapter<Board, RecyclerViewAdapter.ItemViewHolder> {

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd. HH:mm", Locale.KOREA);
        private Consumer<Board> onItemClickListener;

        public FirebaseAuth mAuth =  FirebaseAuth.getInstance();
        public String myUid = mAuth.getUid();

        protected RecyclerViewAdapter() {
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
        }

        public void setOnItemClickListener(Consumer<Board> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemBoardlistBinding binding = ItemBoardlistBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Board item = getItem(position);
            ItemBoardlistBinding binding = holder.binding;

            binding.tvBoardNickName.setText(item.getName());
            binding.tvBoardJoinCnt.setText(String.valueOf(item.getParticipants().size()));
            binding.tvBoardTitle.setText(item.getTitle());
            binding.tvBoardContent.setText(item.getMessage());
            binding.tvBoardDate.setText(dateFormat.format(item.getTimestamp()));

            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.accept(item);
                }
            });

            //신고한 사람목록에 내가 들어가 있으면 해당 글 안보이도록
            if(item.getWhoReport().contains(myUid)){
                binding.tvBoardNickName.setText("");
                binding.tvBoardJoinCnt.setText(String.valueOf(""));
                binding.tvBoardTitle.setText("");
                binding.tvBoardContent.setText("신고하였습니다");
            }
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            public ItemBoardlistBinding binding;

            public ItemViewHolder(ItemBoardlistBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}