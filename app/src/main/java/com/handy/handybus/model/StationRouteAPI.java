package com.handy.handybus.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StationRouteAPI {
    //노선 정보 조회
    String key = "LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";

    public String busRouteId;
    public String stationNm;

    static RequestQueue requestQueue;
    String TAG = "STATION ROUTE";

    ArrayList<StationRoute_Item> mList = new ArrayList<>();
    StationRouteRepository stationRouteRepository= StationRouteRepository.getStationRouteFirst();

    public ArrayList<StationRoute_Item> search_StationRoute(Context root, String busRouteId) throws UnsupportedEncodingException { //edit에 검색어가 들어갈 것

        String location = URLEncoder.encode(busRouteId, "UTF-8");//검색어
        requestQueue = Volley.newRequestQueue(root);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?ServiceKey=" + key + "&busRouteId=" + location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴
                        String curr_tag = "";

                        StationRoute_Item station = new StationRoute_Item();
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
                                    }
                                } else if (eventType == XmlPullParser.END_TAG) {
                                    //itemList 태그 종료시 추가
                                    if (xpp.getName().equals("itemList")) {
                                        if (station.checkRecvAllData()) {

                                        }
                                        addItem(stationNm);
                                    }
                                    curr_tag = "";
                                } else if (eventType == XmlPullParser.TEXT) {
                                    //태그 종류별로 기록
                                    switch (curr_tag) {
                                        case "stationNm": //정류소 이름
                                            station.stationNm = xpp.getText();
                                            stationNm= station.stationNm;

                                            System.out.println(stationNm+"65######");
                                            break;
//                                        case "stationNm":
//                                            bus.busStStationNm = xpp.getText();
//                                            //busStStationNm = xpp.getText();
//                                            break;
                                    }
                                }
                                eventType = xpp.next();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

        return mList;
    }

    public void addItem(String stationNm) {
        StationRoute_Item item= new StationRoute_Item();
        item.setStationNm(stationNm);

        mList.add(item); //왜 이게 2번일어나지?

        System.out.println(item.stationNm+"5######");
        //여기서 리스트를 mutablelivedata로 넣어줌
        stationRouteRepository.updateStationRoute(mList);
    }

}