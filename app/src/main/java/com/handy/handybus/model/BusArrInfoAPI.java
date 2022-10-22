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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusArrInfoAPI {
    //버스 도착 정보 조회
    String key="LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";

    public String busNum;
    public String busRtNm;
    public String busRouteId;
    public String busFirstTm;
    public String busLastTm;
    public String busTerm;
    public String busRouteType;
    public String busAdirection;
    public String busArrmsg1;
    public String busArrmsg2;

    ArrayList<BusList_Item> mList = new ArrayList<>();
    BusListRepository busListRepository= BusListRepository.getBusListFirst();

    static RequestQueue requestQueue;
    String TAG = "BUS LIST";

    public ArrayList<BusList_Item> search_busArrInfo(Context context, String busStID){
        String location = busStID;//검색어

        requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?"+
                        "ServiceKey="+key+"&stId="+location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴

                        String curr_tag = "";
                        BusList_Item bus = new BusList_Item();

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
                                        bus = new BusList_Item();
                                    }
                                } else if (eventType == XmlPullParser.END_TAG) {
                                    //itemList 태그 종료시 추가
                                    if (xpp.getName().equals("itemList")) {
                                        if (bus.checkRecvAllData()) {

                                        }
                                        //도착버스 정보 추가
                                        addItem(busRouteId, busRouteType, busRtNm, busArrmsg1, busArrmsg2 );

                                    }
                                    curr_tag = "";
                                } else if (eventType == XmlPullParser.TEXT) {
                                    //태그 종류별로 기록
                                    switch (curr_tag) {
                                        case "rtNm": //노선명
                                            bus.busRtNm = xpp.getText();
                                            busRtNm=xpp.getText();
                                            break;
                                        case "busRouteId": //노선ID
                                            bus.busRouteId = xpp.getText();
                                            busRouteId=xpp.getText();
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


    public void addItem(String busRouteId, String busRouteType, String busRtNm,String busArrmsg1, String busArrmsg2) {
        BusList_Item item = new BusList_Item();

        item.setBusRouteId(busRouteId);
        item.setBusRouteType(busRouteType);
        item.setBusRtNm(busRtNm);
        item.setBusArrmsg1(busArrmsg1);
        item.setBusArrmsg2(busArrmsg2);
        mList.add(item);

        //여기서 리스트를 mutablelivedata로 넣어줌
        busListRepository.updateList(mList);
    }

}
