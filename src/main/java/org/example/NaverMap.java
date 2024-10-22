package org.example;

/**
 * 네이버맵 API 요청해서 이미지 가져오기
 */
public class NaverMap {

    private final String ID = "omcmy4brbg";
    private final String SECRET = "wkqVbfUYc3geKgklu07N4SAxzgGhqzz1vbQu7esk";

    MainFrame mainFrame; //화면에 표시되고 있는 윈도우창 객체

    public NaverMap(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        String address = mainFrame.addressTxt.getText(); //주소창의 주소를 가져옴.
        System.out.println(address);
    }
}
