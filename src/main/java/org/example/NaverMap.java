package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * 네이버맵 API 요청해서 이미지 가져오기
 */
public class NaverMap {

    private final String ID = "omcmy4brbg";
    private final String SECRET = "wkqVbfUYc3geKgklu07N4SAxzgGhqzz1vbQu7esk";

    MainFrame mainFrame; //화면에 표시되고 있는 윈도우창 객체

    public NaverMap(MainFrame mainFrame) throws IOException, ParseException {
        this.mainFrame = mainFrame;

        String address = mainFrame.addressTxt.getText(); //주소창의 주소를 가져옴.
        //System.out.println(address);
        StringBuilder urlString =
                new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=");
        //입력받은 주소는 한글및 특수기호가 들어가므로 http 주소 요청시 인코딩 필요.
        urlString.append(URLEncoder.encode(address, "UTF-8"));

        //System.out.println(urlString);
        URL url = new URL(urlString.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-type", "application/json"); //내가 보낼때 제이슨
        conn.setRequestProperty("Accept", "application/json"); //받을때 제이슨으로 요청
        conn.setRequestProperty("x-ncp-apigw-api-key-id", ID);
        conn.setRequestProperty("x-ncp-apigw-api-key", SECRET);
        System.out.println("Response Code: " + conn.getResponseCode());

        BufferedReader br;
        if (conn.getResponseCode() == 200) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        String result = br.readLine();

        br.close();  //버퍼드리더 닫기
        conn.disconnect(); //연결객체 닫기

        //System.out.println(result);
        //제이슨 문자열로 결과를 받았음 => 파싱필요
        JSONParser jp = new JSONParser();
        JSONObject jsonObject = (JSONObject) jp.parse(result);
        JSONArray arr = (JSONArray) jsonObject.get("addresses");
        //System.out.println(arr);
        for (Object o : arr) {
            JSONObject obj = (JSONObject) o;
            System.out.println("도로명 주소:" + obj.get("roadAddress"));
            System.out.println("지번 주소:" + obj.get("jibunAddress"));
            System.out.println("경도:" + obj.get("x"));
            System.out.println("위도:" + obj.get("y"));

            String x = obj.get("x").toString(); //경도
            String y = obj.get("y").toString(); //위도
            String z = obj.get("roadAddress").toString(); //도로명주소

            //mapService(x,y,z);
        }
    }
}