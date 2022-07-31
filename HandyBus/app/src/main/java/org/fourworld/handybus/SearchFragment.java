package org.fourworld.handybus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    RecyclerView mRecyclerView = null ;
    RecyclerAdapter_BusStop mAdapter = null ;
    ArrayList<RecylcerItem_BusStop> mList = new ArrayList<>();
    View v;

    EditText edit;
    Button button;

    //정류소 정보 조회
    String key="LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D" ;
    String data;
    String data2;

    String stID = null;
    String busStName= "";
    String busStID = "";

    static RequestQueue requestQueue;
    String TAG = "BUSSTOP LIST";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_search, container, false);

        edit = (EditText) v.findViewById(R.id.edit);
        button = v.findViewById(R.id.button);

        mAdapter = new RecyclerAdapter_BusStop(mList);
        mRecyclerView = v.findViewById(R.id.recycler1);

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(v.getContext());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer();
                String str= edit.getText().toString();//EditText에 작성된 Text얻어오기 -> 검색어 노선명에 들어간것만 busRouteNm
                String location = URLEncoder.encode(str);//검색어

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        "http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByName?ServiceKey="+key+"&stSrch="+location,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse : " + response); //xml 다 가져옴

                                String curr_tag = "";
                                RecylcerItem_BusStop station = new RecylcerItem_BusStop();
                                mAdapter.clearItems();

                                try {
                                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                                    factory.setNamespaceAware(true);
                                    XmlPullParser xpp = factory.newPullParser();

                                    xpp.setInput(new StringReader(response));
                                    int eventType = xpp.getEventType();
                                    while (eventType != XmlPullParser.END_DOCUMENT) {
                                        if (eventType == XmlPullParser.START_DOCUMENT) {
                                            //System.out.println("Start document");
                                        } else if (eventType == XmlPullParser.START_TAG) {
                                            //시작하는 tag 기억
                                            curr_tag = xpp.getName();
                                            if (xpp.getName().equals("item")) {
                                                station = new RecylcerItem_BusStop();
                                            }
                                        } else if (eventType == XmlPullParser.END_TAG) {
                                            //itemList 태그 종료시 추가
                                            if (xpp.getName().equals("itemList")) {
                                                if (station.checkRecvAllData()) {

                                                }
                                                addItem(busStName,busStID); //버스정류소 추가
                                            }
                                            curr_tag = "";
                                        } else if (eventType == XmlPullParser.TEXT) {
                                            //태그 종류별로 기록
                                            switch (curr_tag) {
                                                case "stId":
                                                    station.busStID = xpp.getText(); //이 코드 없으면 오류남
                                                    busStID=xpp.getText();
                                                    break;
                                                case "stNm":
                                                    station.busStName = xpp.getText();
                                                    System.out.println(station.busStName+"3#######");
                                                    busStName=xpp.getText();
                                                    break;
//                                                case "arsno":
//                                                    station.arsno = xpp.getText();
//                                                    break;
//                                                case "gpsx":
//                                                    station.gpsx = xpp.getText();
//                                                    break;
//                                                case "gpsy":
//                                                    station.gpsy = xpp.getText();
//                                                    break;
//                                                case "stoptype":
//                                                    station.stoptype = xpp.getText();
//                                                    break;
                                            }
                                        }
                                        eventType = xpp.next();

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //System.out.println("Count : " + arrStation.size());
                                mAdapter.notifyDataSetChanged();
                                //Toast.makeText(this.onResponse("g"), "수신완료", Toast.LENGTH_LONG).show();

                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse : " + error.toString());
                            }
                        }
                ) {
                    //요청 파라미터를 처리하는 메소드
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //요청 객체가 하나 만들어짐
                        Map<String, String> params = new HashMap<String, String>();
                        //요청 큐에 넣어주면 된다

                        return super.getParams();
                    }
                };
                request.setShouldCache(false);
                requestQueue.add(request);
            }
        });


        return v;
    }


    public void addItem(String busStName, String busStID) {
        RecylcerItem_BusStop item = new RecylcerItem_BusStop();

        item.setBusStName(busStName);
        item.setBusStID(busStID);

        mList.add(item);
    }

}