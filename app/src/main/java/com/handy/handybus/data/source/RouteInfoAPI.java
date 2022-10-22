package com.handy.handybus.data.source;

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
import com.handy.handybus.data.repository.RouteInfoRepository;
import com.handy.handybus.data.model.RouteInfo_Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RouteInfoAPI {
    //노선 정보 조회
    String key = "LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";

//    public String busRouteNm;
//    public String busStStationNm;
//    public String busEdStationNm;
    public String busFirstBusTm;
    public String busLastBusTm;
//    public String busTerm;

    static RequestQueue requestQueue;
    String TAG = "BUS INFO";

    RouteInfo_Item mList = new RouteInfo_Item();
    RouteInfoRepository routeInfoRepository= RouteInfoRepository.getRouteInfoFirst();

    public RouteInfo_Item search_RouteInfo(Context root, String busRouteId) throws UnsupportedEncodingException { //edit에 검색어가 들어갈 것
        mList.busStStationNm = "999";

        String location = URLEncoder.encode(busRouteId, "UTF-8");//검색어
        requestQueue = Volley.newRequestQueue(root);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://ws.bus.go.kr/api/rest/busRouteInfo/getRouteInfo?ServiceKey=" + key + "&busRouteId=" + location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴
                        String curr_tag = "";

                        RouteInfo_Item bus = new RouteInfo_Item();
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

                                    }
                                    addItem(bus);
                                    curr_tag = "";
                                } else if (eventType == XmlPullParser.TEXT) {
                                    //태그 종류별로 기록
                                    switch (curr_tag) {
                                        case "busRouteNm": //노선명
                                            bus.busRouteNm = xpp.getText();
                                            //busRouteNm = xpp.getText();
                                            break;
                                        case "stStationNm":
                                            bus.busStStationNm = xpp.getText();
                                            //busStStationNm = xpp.getText();
                                            break;
                                        case "edStationNm":
                                            bus.busEdStationNm = xpp.getText();
                                            //busEdStationNm = xpp.getText();
                                            break;
                                        case "firstBusTm":
                                            //bus.busStStationNm = xpp.getText(); //오류나는지?
                                            busFirstBusTm = xpp.getText();
                                            //시간 환산 필요 14자리중 뒤에 6자리
                                            String fh = busFirstBusTm.substring(8, 10);//시간
                                            String fm = busFirstBusTm.substring(10, 12);//분
                                            bus.busFirstBusTm = (fh + "시 " + fm + "분");
                                            break;
                                        case "lastBusTm":
                                            //bus.busStStationNm = xpp.getText();
                                            busLastBusTm = xpp.getText();
                                            String lh = busLastBusTm.substring(8, 10);//시간
                                            String lm = busLastBusTm.substring(10, 12);//분
                                            bus.busLastBusTm = (lh + "시 " + lm + "분");
                                            break;
                                        case "term": //분
                                            //busTerm = xpp.getText() ;
                                            bus.busTerm = xpp.getText()+"분";
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

    public void addItem(RouteInfo_Item item) {
//        RouteInfoItem item = new RouteInfoItem();
//
//        item.setBusRouteNm(busRouteNm);
//        item.setBusEdStationNm(busEdStationNm);
//        item.setBusFirstBusTm(busFirstBusTm);
//        item.setBusStStationNm(busStStationNm);
//        item.setBusLastBusTm(busLastBusTm);
//        item.setBusTerm(busTerm);

        //String busRouteNm, String busStStationNm, String busEdStationNm,
        //                        String busFirstBusTm, String busLastBusTm, String busTerm

        mList=item; //바로 아이템을 넣어줌

        //여기서 리스트를 mutablelivedata로 넣어줌
        routeInfoRepository.updateRouteInfo(mList);
    }

}
