package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        JSONArray arr = (JSONArray) jsonObject.get("data");
        //System.out.println(arr);
        for (Object o : arr) {
            JSONObject obj = (JSONObject) o;
            System.out.print(obj.get("id") + "\t");
            System.out.print(obj.get("facilityName") + "\t");
            System.out.print(obj.get("address") + "\t");
            System.out.print(obj.get("org") + "\t");
            System.out.print(obj.get("createdAt") + "\t");
            System.out.println(obj.get("phoneNumber"));
        }

    }
}
