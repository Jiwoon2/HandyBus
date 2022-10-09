package com.khackathon.handybus.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khackathon.handybus.adapter.StationRouteAdapter;
import com.khackathon.handybus.model.StationRouteRepository;
import com.khackathon.handybus.model.StationRoute_Item;
import com.khackathon.handybus.ui.confirmReservation.ConfirmReservationFragment;
import com.khackathon.handybus.MainActivity;
import com.khackathon.handybus.R;
import com.khackathon.handybus.viewmodel.StationRouteViewmodel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReserveActivity extends AppCompatActivity {

//    Intent intent;
//    String busRouteNm;
//    TextView res_busNum;
//    public static TextView et_departures;
//    public static TextView et_arrivals;
//    EditText et_num;
//    EditText et_help;
//
//    Button setbusNm_btn;
//
//    RadioButton radio_adult;
//    RadioButton radio_student;
//    RadioButton radio_child;
//    RadioButton radio_nonWheel;
//    RadioButton radio_hasWheel;
//
//    Button confirm_btn;
//    String radio_type;
//    String radio_wheel;
//
//    RecyclerView mRecyclerView = null;
//    StationRouteAdapter mAdapter = null;
//    StationRouteViewmodel stationRouteViewmodel;
//    ArrayList<StationRoute_Item> mList = new ArrayList<>();
//
//    public static Dialog dialog; // 출력할 Dialog 객체
//
//    public static int selectFlag=-1;
//
//
//    ArrayList<String> temp; //임시 정류소 저장 배열
//    String[] items; //정류소 저장 배열
//
//    String key = "LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";
//
//    public String busRouteId = "";
//    public String stationNm;
//
//    static RequestQueue requestQueue;
//    String TAG = "BUS ID";
//
//    static private MutableLiveData<String> busNm = new MutableLiveData<>();
//
//
//
//    ConfirmReservationFragment ConfirmReservationFragment;
//
//    @SuppressLint("WrongViewCast")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bus_reservation);
//
//        intent= getIntent();
//        busRouteNm= intent.getStringExtra("busRouteNm"); //노선명 넘겨받음
//        System.out.println("2255"+busRouteNm);
//
//        res_busNum=findViewById(R.id.res_busNum);
//        et_departures=findViewById(R.id.et_departures);
//        et_arrivals=findViewById(R.id.et_arrivals);
//        et_num=findViewById(R.id.et_num);
//        et_help=findViewById(R.id.et_help);
//        radio_adult=findViewById(R.id.radio_adult);
//        radio_student=findViewById(R.id.radio_student);
//        radio_child=findViewById(R.id.radio_child);
//        radio_nonWheel=findViewById(R.id.radio_nonWheel);
//        radio_hasWheel=findViewById(R.id.radio_hasWheel);
//        confirm_btn=findViewById(R.id.confirm_btn);
//
//        setbusNm_btn = findViewById(R.id.setbusNm_btn);
//
//        res_busNum.setText(busRouteNm);
//        ConfirmReservationFragment = new ConfirmReservationFragment();
//
//        confirm_btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                intent= new Intent(v.getContext(), MainActivity.class);
//
//                intent.putExtra("asdf", "ㄹㄷㄴㄷ");
////                intent.putExtra("et_departures", String.valueOf(et_departures.getText()));
////                intent.putExtra("et_arrivals", String.valueOf(et_arrivals.getText()));
////                intent.putExtra("radio_type",radio_type);
////                intent.putExtra("res_busNum", String.valueOf(res_busNum.getText()));
////                intent.putExtra("et_num", String.valueOf(et_num.getText()));
////                intent.putExtra("radio_wheel",radio_wheel);
////                intent.putExtra("et_help", String.valueOf(et_help.getText()));
//
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); //이전 액티비티 모두 종료
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //이전 액티비티 모두 종료
//                startActivity(intent);
//
//                //화면 전환시 텀 없애기
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                ((Activity)v.getContext()).overridePendingTransition(0,0);
//            }
//        });
//
//        //경유 정류소
//        stationRouteViewmodel = new StationRouteViewmodel(StationRouteRepository.getStationRouteFirst());
//
//        mAdapter = new StationRouteAdapter(mList);
//
//        //버스 번호 결정
//        setbusNm_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //옆에 확인버튼 누름
//                busRouteNm = res_busNum.getText().toString();
//
//                try {
//                    //여기서 함수 사용
//                    busRouteId = search_BusID(getApplication(), busRouteNm);
//
//                    busNm.observe(ReserveActivity.this, new Observer<String>() {
//                        @Override
//                        public void onChanged(String stirng) {
//                            mAdapter.clearItems(); //목록 초기화
//                        }
//                    });
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//
//        et_departures.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                selectFlag=0;
//
//                try {
//                    mList = stationRouteViewmodel.useStationRoute(v.getContext(), busRouteId);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//
//                stationRouteViewmodel.updateStationRoute(mList);
//
//                stationRouteViewmodel.getStationRouteCurrentItem().observe(ReserveActivity.this, new androidx.lifecycle.Observer<ArrayList<StationRoute_Item>>() {
//                    @Override
//                    public void onChanged(ArrayList<StationRoute_Item> item) {
//                        mAdapter.setItemsList((ArrayList<StationRoute_Item>) item);
//                    }
//                });
//
//                changeArr(); //배열 변경
//                showAlertDialogStation();
//
//            }
//        });
//
//        et_arrivals.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                selectFlag=1;
//
//                try {
//                    mList = stationRouteViewmodel.useStationRoute(v.getContext(), busRouteId);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                stationRouteViewmodel.updateStationRoute(mList);
//
//                stationRouteViewmodel.getStationRouteCurrentItem().observe(ReserveActivity.this, new androidx.lifecycle.Observer<ArrayList<StationRoute_Item>>() {
//                    @Override
//                    public void onChanged(ArrayList<StationRoute_Item> item) {
//                        mAdapter.setItemsList((ArrayList<StationRoute_Item>) item);
//
//                    }
//                });
//
//                changeArr(); //배열 변경
//                showAlertDialogStation();
//            }
//        });
//
//
//    }
//
//
//    private void showAlertDialogStation() {
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        dialog = new Dialog(this);
//
//        display.getRealSize(size);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//
//        LayoutInflater inf = getLayoutInflater();
//        View dialogView = inf.inflate(R.layout.activity_stationfromto_list, null);
//
//        TextView text_fromto = dialogView.findViewById(R.id.text_fromto);
//        if(selectFlag==0){
//            text_fromto.setText("출발지 선택");
//        }
//        else if(selectFlag==1){
//            text_fromto.setText("도착지 선택");
//        }
//
//        // Dialog layout 선언
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        int width = size.x;
//        lp.width = width * 95 / 100; // 사용자 화면의 95%
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 높이는 내용 전체 높이만큼
//        dialog.setContentView(dialogView); // Dialog에 선언했던 layout 적용
//        dialog.setCanceledOnTouchOutside(true); // 외부 touch 시 Dialog 종료
//        dialog.getWindow().setAttributes(lp); // 지정한 너비, 높이 값 Dialog에 적용
//        ArrayList<String> arrayList = new ArrayList<>(); // recyclerView에 들어갈 Array
//        arrayList.addAll(Arrays.asList(items)); // Array에 사전에 정의한 정류소명 배열 넣기
//        /*
//        다음 4줄의 코드는 RecyclerView를 정의하기 위한 View, Adapter선언 코드이다.
//        1. RecyclerView id 등록
//        2. 수직방향으로 보여줄 예정이므로 LinearLayoutManager 등록
//           2차원이면 GridLayoutManager 등 다른 Layout을 선택
//        3. adapter에 topic Array 넘겨서 출력되게끔 전달
//        4. adapter 적용
//        */
//        mRecyclerView = (RecyclerView) dialogView.findViewById(R.id.container_stRoute2);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mAdapter);
//        dialog.show(); // Dialog 출력
//    }
//
//
//    //mList 안의 정류소명을 string 배열로 변환
//    public void changeArr() {
//        temp = new ArrayList<>();
//        items = new String[]{};
//
//        System.out.println(items.length + "ㅁㅁㅁㅁㅁ2###"); //계속0인데
//
//        //여기서 mList의 사이즈가 있는 이유는...?? 왤까..
//        for (int i = 0; i < mList.size(); i++) {
//            temp.add(mList.get(i).stationNm);
//        }
//        System.out.println(temp.size() + "ㅁㅁㅁㅁㅁ3###"); //temp가 계속 쌓여.. 왤까? ...
//        //mList가 계속 누적되니까..ㅋㅋ...ㅋ... 하..... 그럼 누적이 안되게 해야되는데
//
//        items = temp.toArray(new String[temp.size()]); //items에 복사
//    }
//
//    public String search_BusID(Context root, String stationNm) throws UnsupportedEncodingException { //edit에 검색어가 들어갈 것
//
//        String location = URLEncoder.encode(stationNm, "UTF-8");//검색어
//        requestQueue = Volley.newRequestQueue(root);
//
//        StringRequest request = new StringRequest(
//                Request.Method.GET,
//                "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=" + key + "&strSrch=" + location,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴
//                        String curr_tag = "";
//
//                        try {
//                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//                            factory.setNamespaceAware(true);
//                            XmlPullParser xpp = factory.newPullParser();
//
//                            xpp.setInput(new StringReader(response));
//                            int eventType = xpp.getEventType();
//                            while (eventType != XmlPullParser.END_DOCUMENT) {
//                                if (eventType == XmlPullParser.START_DOCUMENT) {
//                                    //System.out.println("Start document");
//                                } else if (eventType == XmlPullParser.START_TAG) {
//                                    //시작하는 tag 기억
//                                    curr_tag = xpp.getName();
//                                    if (xpp.getName().equals("item")) {
//                                    }
//                                } else if (eventType == XmlPullParser.END_TAG) {
//                                    //itemList 태그 종료시 추가
//                                    if (xpp.getName().equals("itemList")) {
//
//
//                                    }
//                                    curr_tag = "";
//                                } else if (eventType == XmlPullParser.TEXT) {
//                                    //태그 종류별로 기록
//                                    switch (curr_tag) {
//                                        case "busRouteId": //버스 ID
//                                            busRouteId = xpp.getText();
//                                            //mutablelivedata에 할당
//                                            busNm.postValue(busRouteId);
//                                            System.out.println(busRouteId + "244####");
////                                            System.out.println(num.getValue() + "99244####");
//
//                                            break;
//                                    }
//                                }
//                                eventType = xpp.next();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse : " + error.toString());
//                    }
//                }
//        ) {
//            //요청 파라미터를 처리하는 메소드
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                //요청 객체가 하나 만들어짐
//                Map<String, String> params = new HashMap<String, String>();
//                //요청 큐에 넣어주면 된다
//
//                return super.getParams();
//            }
//        };
//        request.setShouldCache(false);
//        requestQueue.add(request);
//
//        return busRouteId;
//    }
//
//
//
//    public void onRadioButtonClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch(view.getId()) {
//            case R.id.radio_adult:
//                if (checked){
//                    radio_type="일반";
//                    //tv_personNm.setText("일반 ");
//                    //tv_personNm.append(et_num+"명");
//                }
//            else
//                // Remove the meat
//                break;
//            case R.id.radio_student:
//                if (checked){
//
//                    radio_type="학생";
//                }
//            else
//                break;
//            case R.id.radio_child:
//                if (checked){
//                    radio_type="어린이";
//
//                }
//            else
//                break;
//            case R.id.radio_nonWheel:
//                if (checked){
//                    radio_wheel="휠체어 해당없음";
//
//                }
//                else
//                    break;
//            case R.id.radio_hasWheel:
//                if (checked){
//                    radio_wheel="휠체어 소지";
//
//                }
//                else
//                    break;
//        }
//    }
//
//    //이전화면으로 이동시 텀 없애기
//    @Override
//    protected void onPause() {
//        super.onPause();
//        overridePendingTransition(0, 0);
//    }
}