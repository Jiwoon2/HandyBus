package org.fourworld.handybus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.Map;

public class BusInfo extends AppCompatActivity {
    String key="LGXl5bldirXE69OTG08x3xUC%2F90KSnwDlSqI6ZEtNMZNnCLMHgjDTRX1iUdqFmf7%2BOZf2esBogmwqYVV%2B8a40g%3D%3D";

    Intent intent;
    String busRouteId;//노선ID
    String busRouteNm;//노선명
    String stStationNm; //기점
    String edStationNm; //종점
    String firstBusTm; //첫차 -> 저상버스는 정보 안나오거나 19년도에서 멈춘 정보많아서 일반버스로 함
    String lastBusTm; //막차
    String term; //배차 간격(일반버스 기준)

    TextView tv_busRouteNm;
    TextView tv_stStationNm;
    TextView tv_edStationNm;
    TextView tv_firstBusTm;
    TextView tv_lastBusTm;
    TextView tv_term;

    static RequestQueue requestQueue;
    String TAG = "BUS INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        tv_busRouteNm=findViewById(R.id.tv_busRouteNm);
        tv_stStationNm=findViewById(R.id.tv_stStationNm);
        tv_edStationNm=findViewById(R.id.tv_edStationNm);
        tv_firstBusTm=findViewById(R.id.tv_firstBusTm);
        tv_lastBusTm=findViewById(R.id.tv_lastBusTm);
        tv_term=findViewById(R.id.tv_term);

        intent=getIntent();
        busRouteId=intent.getStringExtra("busRouteId");

        requestQueue = Volley.newRequestQueue(this);

        StringBuffer buffer=new StringBuffer();
        String location = URLEncoder.encode(busRouteId);//검색어

        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://ws.bus.go.kr/api/rest/busRouteInfo/getRouteInfo?ServiceKey="+key+"&busRouteId="+location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse : " + response); //xml 다 가져옴
                        String curr_tag = "";
//System.out.println(location+"%%%%%");
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
                                    curr_tag = "";
                                } else if (eventType == XmlPullParser.TEXT) {
                                    //태그 종류별로 기록
                                    switch (curr_tag) {
                                        case "busRouteNm": //노선명
                                            busRouteNm=xpp.getText();
                                            tv_busRouteNm.setText(busRouteNm);
                                            break;
                                        case "stStationNm":
                                            stStationNm=xpp.getText();
                                            tv_stStationNm.setText(stStationNm);
                                            break;
                                        case "edStationNm":
                                            edStationNm=xpp.getText();
                                            tv_edStationNm.setText(edStationNm);
                                            break;
                                        case "firstBusTm":
                                            firstBusTm=xpp.getText();
                                            //시간 환산 필요
                                            tv_firstBusTm.setText(firstBusTm);
                                            break;
                                        case "lastBusTm":
                                            lastBusTm=xpp.getText();
                                            tv_lastBusTm.setText(lastBusTm);
                                            break;
                                        case "term": //분
                                            term=xpp.getText();
                                            tv_term.setText(term);
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


    }
}