package com.handy.handybus.ui.main.children;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.handy.handybus.ui.adapter.BusStopAdapter;
import com.handy.handybus.databinding.FragmentSearchBinding;
import com.handy.handybus.data.repository.BusStopRepository;
import com.handy.handybus.data.model.BusStop_Item;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private BusStopViewmodel busstopViewmodel;

    private final ArrayList<BusStop_Item> list = new ArrayList<>();
    private BusStopAdapter adapter = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        FragmentSearchBinding fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
//        root= fragmentSearchBinding.getRoot();

        //v = inflater.inflate(R.layout.fragment_search, container, false);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        binding.recyclerView.setHasFixedSize(true); //아이템 크기 고정

        busstopViewmodel = new BusStopViewmodel(BusStopRepository.getBusStopFirst());

        //mList= busstopViewmodel.getBusStopRepository(); //없어도 상관 x

        adapter = new BusStopAdapter(list);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

//        Button btnn= fragmentSearchBinding.btnn;
//        btnn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //((MainActivity)getActivity()).replaceFragment(new BlankFragment()); //프래그먼트로 전환
//            }
//        });
//        Button btnn=v.findViewById(R.id.btnn);
//        btnn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //이거 누르면 리스트의 몇번째거 가졍게 해보기
//                Log.d("Hhhhhhhh2222", mList.get(5).getBusStName());
//                Log.d("Hhhhhhhh2222", mList.get(5).getBusStID());
//
//
//
//            }
//        });

//        button=v.findViewById(R.id.button);
//        EditText edit=v.findViewById(R.id.edit);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                adapter.clearItems();
                String query = binding.edit.getText().toString().trim();

                busstopViewmodel.clearBusStop(list); //목록 초기화

                try {
                    busstopViewmodel.useStationName(binding.getRoot(), query); //API로 검색

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // busstopViewmodel.updateBusStop(mList); //mutable에 갱신해줌
            }
        });

        busstopViewmodel.getBusStopCurrentItem().observe(getViewLifecycleOwner(), list -> {
            adapter.setItemsList(list);
        });

        return binding.getRoot();
    }

    private void hideKeyboard() {
        try {
            InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignore) {
        }
    }
}