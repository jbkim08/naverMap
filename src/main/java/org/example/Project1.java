package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Project1 {
    public static void main(String[] args) throws IOException, ParseException {
        /* 주소를 입력받기 */
        Scanner sc = new Scanner(System.in);
        System.out.print("주소를 입력: ");
        String address = sc.nextLine(); //주소를 받을때까지 대기함
        sc.close();
        System.out.println("받은 주소는 : " + address);

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
        conn.setRequestProperty("x-ncp-apigw-api-key-id", "omcmy4brbg");
        conn.setRequestProperty("x-ncp-apigw-api-key", "wkqVbfUYc3geKgklu07N4SAxzgGhqzz1vbQu7esk");
        System.out.println("Response Code: " + conn.getResponseCode());

        BufferedReader br;
        if(conn.getResponseCode() == 200) {
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

            mapService(x,y,z);
        }

    }

    private static void mapService(String x, String y, String z) throws UnsupportedEncodingException {
        //네이버 Static Map 서비스로 이미지를 가져오기!
        String mapUrl = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
        String pos = URLEncoder.encode(x + " " + y, "UTF-8");
        //w=300&h=300&center=127.1054221,37.3591614&level=16
        mapUrl += "center=" + x + "," + y; //x,y 좌표
        mapUrl += "&level=16&w=700&h=500"; //줌(1~20), 가로이미지 700 세로 500
        mapUrl += "&markers=type:t|size:mid:pos:"+pos+"|label:"+URLEncoder.encode(z, "UTF-8");
        System.out.println(mapUrl);

    }
}
