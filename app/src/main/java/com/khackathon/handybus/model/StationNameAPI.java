package com.khackathon.handybus.model;

import android.util.Log;
import android.view.View;

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

public class StationNameAPI {
    //정류소 정보 조회
    String key="LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D" ;

    static RequestQueue requestQueue;
    String TAG = "BUSSTOP LIST";

    ArrayList<BusStop_Item> mList = new ArrayList<>();
    String busStName= "";
    String busStID = "";
    String busArsId ="";

    BusStopRepository busStopRepository= BusStopRepository.getBusStopFirst();

    public ArrayList<BusStop_Item> search_stationName(View root, String busRouteNm) throws UnsupportedEncodingException { //edit에 검색어가 들어갈 것
        String str= busRouteNm;//검색어 노선명에 들어간것만 busRouteNm
        String location = URLEncoder.encode(str,"UTF-8");//검색어
        requestQueue = Volley.newRequestQueue(root.getContext());

        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByName?"
                        +"ServiceKey="+key+"&stSrch="+location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴

                        String curr_tag = "";
                        BusStop_Item station = new BusStop_Item();

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
                                        station = new BusStop_Item();
                                    }
                                } else if (eventType == XmlPullParser.END_TAG) {
                                    //itemList 태그 종료시 추가
                                    if (xpp.getName().equals("itemList")) {
                                        if (station.checkRecvAllData()) {

                                        }
                                        //mList.add(station);
                                        addItem(busStName, busStID, busArsId); //버스정류소 추가
                                    }
                                    curr_tag = "";
                                } else if (eventType == XmlPullParser.TEXT) {
                                    //태그 종류별로 기록
                                    switch (curr_tag) {
                                        case "stId": //정류소 ID
                                            station.busStID = xpp.getText(); //이 코드 없으면 오류남
                                            busStID=xpp.getText();
                                            break;
                                        case "stNm": //정류소명
                                            station.busStName = xpp.getText();
                                            System.out.println(station.busStName+"3#######");
                                            busStName=xpp.getText();
                                            break;
                                        case "arsId": //정류소 고유번호
                                            station.busArsId = xpp.getText();
                                            busArsId=xpp.getText();
                                            break;

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



    public void addItem(String busStName, String busStID, String busArsId) {
        BusStop_Item item = new BusStop_Item();

        item.setBusStName(busStName);
        item.setBusStID(busStID);
        item.setBusArsId(busArsId);

        mList.add(item);

        //리스트를 mutablelivedata로 넣어줌
        busStopRepository.updateBusStop(mList);
    }



}
