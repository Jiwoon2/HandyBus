package org.fourworld.handybus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class BusList extends AppCompatActivity {

    String key="LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";

    Intent intent;
    String busStID;
    String busRouteType;
    String busNum;
    String busArrmsg1;
    String busArrmsg2;

    RecyclerView mRecyclerView = null ;
    RecyclerAdapter_BusList mAdapter = null ;
    ArrayList<RecyclerItem_BusList> mList = new ArrayList<>();

    static RequestQueue requestQueue;
    String TAG = "BUS LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        intent= getIntent();
        busStID= intent.getStringExtra("busStID"); //정류소 id 넘겨받음

        mAdapter = new RecyclerAdapter_BusList(mList);
        mRecyclerView = this.findViewById(R.id.container_busList);

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(this);

            StringBuffer buffer=new StringBuffer();
            String location = URLEncoder.encode(busStID);//검색어

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?ServiceKey="+key+"&stId="+location,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse : " + response); //xml 다 가져옴

                            String curr_tag = "";
                            RecyclerItem_BusList bus = new RecyclerItem_BusList();
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
                                            bus = new RecyclerItem_BusList();
                                        }
                                    } else if (eventType == XmlPullParser.END_TAG) {
                                        //itemList 태그 종료시 추가
                                        if (xpp.getName().equals("itemList")) {
                                            if (bus.checkRecvAllData()) {

                                            }
                                            addItem(busRouteType,busNum, busArrmsg1,busArrmsg2 ); //버스정류소 추가
                                        }
                                        curr_tag = "";
                                    } else if (eventType == XmlPullParser.TEXT) {
                                        //태그 종류별로 기록
                                        switch (curr_tag) {
                                            case "rtNm": //노선명
                                                bus.busNum = xpp.getText();
                                                busNum=xpp.getText();
                                                break;
                                            case "routeType"://노선 유형
                                                bus.busRouteType = xpp.getText();
                                                busRouteType=xpp.getText();
                                                break;
                                            case "arrmsg1": //도착 버스1
                                                bus.busArrmsg1 = xpp.getText();
                                                busArrmsg1=xpp.getText();
                                                break;
                                            case "arrmsg2":  //도착 버스2
                                                bus.busArrmsg2 = xpp.getText();
                                                busArrmsg2=xpp.getText();
                                                break;
//                                                case "stoptype":
//                                                    station.stoptype = xpp.getText();
//                                                    break;

//                                            case "stId": //정류소 id
//                                                bus.busStID = xpp.getText(); //이 코드 없으면 오류남
//                                                busStID=xpp.getText();
//                                                break;
                                        }
                                    }
                                    eventType = xpp.next();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            mAdapter.notifyDataSetChanged();

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

    public void addItem(String busRouteType, String busNum,String busArrmsg1,String busArrmsg2) {
        RecyclerItem_BusList item = new RecyclerItem_BusList();

        item.setBusRouteType(busRouteType);
        item.setBusNum(busNum);
        item.setBusArrmsg1(busArrmsg1);
        item.setBusArrmsg2(busArrmsg2);

        mList.add(item);
    }
}