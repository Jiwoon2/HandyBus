package com.handy.handybus.ui.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.handy.handybus.databinding.FragmentPostBinding;

public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    private PostViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        binding.doneBtn.setOnClickListener(v -> post());
    }

    private void post() {
        String title = binding.titleTextField.getEditText().getText().toString().trim();
        String message = binding.messageTextField.getEditText().getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(requireContext().getApplicationContext(), "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.isEmpty()) {
            Toast.makeText(requireContext().getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.post(title, message);
        Toast.makeText(requireContext().getApplicationContext(), "게시되었습니다.", Toast.LENGTH_SHORT).show();

        requireActivity().onBackPressed();
    }
}
