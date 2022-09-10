package com.khackathon.handybus.view.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.adapter.BusStopAdapter;
import com.khackathon.handybus.databinding.FragmentSearchBinding;
import com.khackathon.handybus.model.BusStopRepository;
import com.khackathon.handybus.model.BusStop_Item;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private BusStopViewmodel busstopViewmodel;
    View root;

    ArrayList<BusStop_Item> mList = new ArrayList<>();

    RecyclerView mRecyclerView = null ;
    BusStopAdapter mAdapter = null ;

    ImageButton button;
    String editText;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        FragmentSearchBinding fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
//        root= fragmentSearchBinding.getRoot();

        //v = inflater.inflate(R.layout.fragment_search, container, false);


        FragmentSearchBinding fragmentSearchBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false);
        root= fragmentSearchBinding.getRoot();

        mRecyclerView = fragmentSearchBinding.containerBusStop;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentSearchBinding.getRoot().getContext()));
        mRecyclerView.setHasFixedSize(true); //아이템 크기 고정

        busstopViewmodel= new BusStopViewmodel(BusStopRepository.getBusStopFirst());
        fragmentSearchBinding.setBusstopviewmodel(busstopViewmodel);

        //mList= busstopViewmodel.getBusStopRepository(); //없어도 상관 x

        mAdapter = new BusStopAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

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

        button= fragmentSearchBinding.button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdapter.clearItems();
                editText= fragmentSearchBinding.edit.getText().toString(); //검색어

                mList= busstopViewmodel.clearBusStop(mList); //목록 초기화

                try {
                    mList= busstopViewmodel.useStationName(root, editText); //API로 검색

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                busstopViewmodel.updateBusStop(mList); //mutable에 갱신해줌

            }
        });

        busstopViewmodel.getBusStopCurrentItem().observe(this,  (Observer<ArrayList<BusStop_Item>>) list->  {
            mAdapter.setItemsList((ArrayList<BusStop_Item>) list);

        });

        return root;
    }

}