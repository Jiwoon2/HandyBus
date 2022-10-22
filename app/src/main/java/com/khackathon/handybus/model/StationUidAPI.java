//package com.fourworld.example_mvvm4.model;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.fourworld.example_mvvm4.R;
//import com.fourworld.example_mvvm4.adapter.RecyclerAdapter_BusList;
//import com.fourworld.example_mvvm4.view.BusList;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.StringReader;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
////고유번호에 해당하는 정류소의 저상버스 도착정보
//public class StationUidAPI {
//
//    String key="LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";
//
//    public String busNum;
//    public String busRtNm;
//    public String busStId;
//    public String busFirstTm;
//    public String busLastTm;
//    public String busTerm;
//    public String busRouteType;
//    public String busAdirection;
//    public String busArrmsg1;
//    public String busArrmsg2;
//
//    ArrayList<RecyclerItem_BusList> mList = new ArrayList<>();
//
//    static RequestQueue requestQueue;
//    String TAG = "BUS LIST";
//
//    public ArrayList<RecyclerItem_BusList> search_stationName(Context context, String busArsId){
//        String location = busArsId;//검색어
//        System.out.println("22222222hhhhhh"+location);
//
//        requestQueue = Volley.newRequestQueue(context);
//
//        StringRequest request = new StringRequest(
//                Request.Method.GET,
//                "http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByUid?"+
//                        "ServiceKey="+key+"&arsId="+location,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴
//
//                        String curr_tag = "";
//                        RecyclerItem_BusList bus = new RecyclerItem_BusList();
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
//                                        bus = new RecyclerItem_BusList();
//                                    }
//                                } else if (eventType == XmlPullParser.END_TAG) {
//                                    //itemList 태그 종료시 추가
//                                    if (xpp.getName().equals("itemList")) {
//                                        if (bus.checkRecvAllData()) {
//
//                                        }
//                                        //도착버스 정보 추가
//                                        addItem(busRtNm, busStId, busFirstTm, busLastTm,
//                                                busTerm, busRouteType,busAdirection, busArrmsg1, busArrmsg2 );
//                                    }
//                                    curr_tag = "";
//                                } else if (eventType == XmlPullParser.TEXT) {
//                                    //태그 종류별로 기록
//                                    switch (curr_tag) {
//                                        case "rtNm": //노선명
//                                            bus.busRtNm = xpp.getText();
//                                            busRtNm=xpp.getText();
//                                            break;
//                                        case "stId": //정류소 ID---
//                                            bus.busStId = xpp.getText();
//                                            busStId=xpp.getText();
//                                            break;
//                                        case "firstTm"://첫차 시간
//                                            bus.busFirstTm = xpp.getText();
//                                            busFirstTm=xpp.getText();
//                                            break;
//                                        case "lastTm": //막차 시간
//                                            bus.busLastTm = xpp.getText();
//                                            busLastTm=xpp.getText();
//                                            break;
//                                        case "term": //배차간격
//                                            bus.busTerm = xpp.getText();
//                                            busTerm=xpp.getText();
//                                            break;
//                                        case "routeType"://노선 유형
//                                            bus.busRouteType = xpp.getText();
//                                            busRouteType=xpp.getText();
//                                            break;
//                                        case "adirection":  //방향
//                                            bus.busAdirection = xpp.getText();
//                                            busAdirection=xpp.getText();
//                                            break;
//                                        case "arrmsg1": //도착 버스1
//                                            bus.busArrmsg1 = xpp.getText();
//                                            busArrmsg1=xpp.getText();
//                                            break;
//                                        case "arrmsg2":  //도착 버스2
//                                            bus.busArrmsg2 = xpp.getText();
//                                            busArrmsg2=xpp.getText();
//                                            break;
//                                    }
//                                }
//                                eventType = xpp.next();
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
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
//        return mList;
//    }
//
//
//    public void addItem(String busRtNm,String busStId,String busFirstTm, String busLastTm,
//                        String busTerm, String busRouteType, String busAdirection, String busArrmsg1, String busArrmsg2) {
//        RecyclerItem_BusList item = new RecyclerItem_BusList();
//
//        item.setBusRtNm(busRtNm);
//        item.setBusFirstTm(busFirstTm);
//        item.setBusLastTm(busLastTm);
//        item.setBusTerm(busTerm);
//        item.setBusRouteType(busRouteType);
//        item.setBusAdirection(busAdirection);
//        item.setBusArrmsg1(busArrmsg1);
//        item.setBusArrmsg2(busArrmsg2);
//
//        mList.add(item);
//
//
//        //여기서 리스트를 mutablelivedata로 넣어줌
//        //busStopRepository.updateList(mList);
//    }
//
//
//}
