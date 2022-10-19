package com.handy.handybus.ui.main.children;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.handy.handybus.data.model.UserReserv_Item;
import com.handy.handybus.databinding.FragmentReservationBinding;
import com.handy.handybus.ui.reservation.BusPointDialog;
import com.handy.handybus.ui.reservation.BusSearchDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//ReserveActivity과 동일
public class ReservationFragment extends Fragment {

    public static ReservationFragment getInstance(UserReserv_Item item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("reservation", item);

        ReservationFragment fragment = new ReservationFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private UserReserv_Item reservation;
    private FragmentReservationBinding binding;
    private ReservationViewModel viewModel;

    private int personNum = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            reservation = getArguments().getParcelable("reservation");
        }

        if (savedInstanceState != null) {
            reservation = savedInstanceState.getParcelable("reservation");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("reservation", reservation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReservationBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, new ReservationViewModelProvider(binding.getRoot().getContext())).get(ReservationViewModel.class);
        viewModel.routeItems.observe(getViewLifecycleOwner(), routes -> {
        });

        //탑승 인원 수
        binding.numMinus.setOnClickListener(v -> {
            personNum -= 1;
            personNum = Math.max(personNum, 0);
            binding.tvNum.setText(personNum + "");
            binding.tvShowNum.setText("성인 " + personNum + "명");
        });

        binding.numPlus.setOnClickListener(v -> {
            personNum += 1;
            binding.tvNum.setText(personNum + "");
            binding.tvShowNum.setText("성인 " + personNum + "명");
        });

        //예약 버튼
        binding.confirmBtn.setOnClickListener(v -> {
            hideKeyboard();

            reservation();
        });

        //region Bus route id
        ((AutoCompleteTextView) binding.busNumberField.getEditText()).setOnClickListener(view -> {
            if (getChildFragmentManager().findFragmentByTag("Search") == null) {
                new BusSearchDialog().show(getChildFragmentManager(), "Search");
            }
        });

        binding.busNumberField.setEndIconOnClickListener(view -> {
            if (getChildFragmentManager().findFragmentByTag("Search") == null) {
                new BusSearchDialog().show(getChildFragmentManager(), "Search");
            }
        });

        getChildFragmentManager().setFragmentResultListener("busRoute", getViewLifecycleOwner(), (requestKey, result) -> {
            String busName = result.getString("busName");
            String busRouteId = result.getString("busRouteId");

            if (busName != null && busRouteId != null) {
                viewModel.setBusRouteId(busRouteId);

                ((AutoCompleteTextView) binding.busNumberField.getEditText()).setText(busName, false);
                binding.etDepartures.setText("");
                binding.etArrivals.setText("");
            }
        });
        //endregion

        //region Departure point
        binding.etDepartures.setOnClickListener(view -> {
            if (viewModel.routeItems.getValue() == null || viewModel.routeItems.getValue().isEmpty()) {
                Toast.makeText(requireContext().getApplicationContext(), "먼저 버스 번호를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getChildFragmentManager().findFragmentByTag("DeparturePoint") == null) {
                BusPointDialog.getInstance(true).show(getChildFragmentManager(), "DeparturePoint");
            }
        });

        getChildFragmentManager().setFragmentResultListener("departurePoint", getViewLifecycleOwner(), (requestKey, result) -> {
            String point = result.getString("stationName");

            if (point != null) {
                binding.etDepartures.setText(point);
            }
        });
        //endregion

        //region Arrival point
        binding.etArrivals.setOnClickListener(view -> {
            if (viewModel.routeItems.getValue() == null || viewModel.routeItems.getValue().isEmpty()) {
                Toast.makeText(requireContext().getApplicationContext(), "먼저 버스 번호를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getChildFragmentManager().findFragmentByTag("ArrivalPoint") == null) {
                BusPointDialog.getInstance(false).show(getChildFragmentManager(), "ArrivalPoint");
            }
        });

        getChildFragmentManager().setFragmentResultListener("arrivalPoint", getViewLifecycleOwner(), (requestKey, result) -> {
            String point = result.getString("stationName");

            if (point != null) {
                binding.etArrivals.setText(point);
            }
        });
        //endregion

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (reservation != null) {
            binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

            binding.title.setText("예약 수정");

            binding.confirmBtn.setText("수정하기");
            binding.confirmBtn.setOnClickListener(v -> update());

            viewModel.setBusRouteId(reservation.getResBusRouteId());

            binding.etDepartures.setText(reservation.getResDepartures());
            binding.etArrivals.setText(reservation.getResArrivals());
            binding.resBusNum.setText(reservation.getResBusNum(), false);

            personNum = Integer.parseInt(reservation.getResNum());
            binding.tvNum.setText(personNum + "");
            binding.tvShowNum.setText("성인 " + personNum + "명");

            if (TextUtils.equals(reservation.getResWheel(), binding.radioNonWheel.getText().toString())) {
                binding.radioNonWheel.setChecked(true);

            } else if (TextUtils.equals(reservation.getResWheel(), binding.radioHasWheel.getText().toString())) {
                binding.radioHasWheel.setChecked(true);
            }

            binding.etHelp.setText(reservation.getResHelp());

        } else {
            binding.toolbar.setNavigationIcon(null);
        }
    }

    private void reservation() {
        UserReserv_Item item = new UserReserv_Item();
        item.setResDepartures(binding.etDepartures.getText().toString());
        item.setResArrivals(binding.etArrivals.getText().toString());
        item.setResBusNum(binding.resBusNum.getText().toString());
        item.setResBusRouteId(viewModel.selectedBusRouteId.getValue());
        item.setResNum(binding.tvNum.getText().toString());
        item.setResWheel(binding.radioNonWheel.isChecked()
                ? binding.radioNonWheel.getText().toString()
                : (binding.radioHasWheel.isChecked() ? binding.radioHasWheel.getText().toString() : ""));
        item.setResHelp(binding.etHelp.getText().toString());
        item.setResDate(getDate());

        viewModel.reservation(item);

        Toast toast = Toast.makeText(getContext(), "예약되었습니다", Toast.LENGTH_SHORT);
        toast.show();

        //예약 정보 없애기
        viewModel.setBusRouteId("");

        personNum = 0;
        binding.tvNum.setText("0");
        binding.tvShowNum.setText("인원");

        binding.resBusNum.setText("");
        binding.etArrivals.setText("");
        binding.etDepartures.setText("");
        binding.wheelchairRadioGroup.clearCheck();
        binding.etHelp.setText("");
    }

    private void update() {
        UserReserv_Item item = new UserReserv_Item();
        item.setResDepartures(binding.etDepartures.getText().toString());
        item.setResArrivals(binding.etArrivals.getText().toString());
        item.setResBusNum(binding.resBusNum.getText().toString());
        item.setResBusRouteId(viewModel.selectedBusRouteId.getValue());
        item.setResNum(binding.tvNum.getText().toString());
        item.setResWheel(binding.radioNonWheel.isChecked()
                ? binding.radioNonWheel.getText().toString()
                : (binding.radioHasWheel.isChecked() ? binding.radioHasWheel.getText().toString() : ""));
        item.setResHelp(binding.etHelp.getText().toString());
        item.setResDate(reservation.getResDate());
        item.setKey(reservation.getKey());

        viewModel.update(item);

        Toast toast = Toast.makeText(getContext(), "수정되었습니다", Toast.LENGTH_SHORT);
        toast.show();

        requireActivity().onBackPressed();
    }

    // 날짜
    private String getDate() {
        long now = System.currentTimeMillis(); //현재 시간
        Date date = new Date(now); //date 형식으로 변경
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM/dd hh:mm", Locale.KOREA); //포맷설정

        return dateFormat.format(date);
    }

    private void hideKeyboard() {
        try {
            InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignore) {
        }
    }
}