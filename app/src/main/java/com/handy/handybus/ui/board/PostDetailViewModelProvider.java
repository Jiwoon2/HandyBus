package com.handy.handybus.ui.board;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.handy.handybus.data.model.Board;

public class PostDetailViewModelProvider implements ViewModelProvider.Factory {

    final Board board;

    public PostDetailViewModelProvider(Board board) {
        this.board = board;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostDetailViewModel(board);
    }
}
