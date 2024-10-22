package org.example;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {

    public JTextField addressTxt;  //주소 입력창
    public JLabel resAddress, jibunAddress, resX, resY, imageLabel;

    public MainFrame(String title) {
        super(title);
        //위쪽패널에 주소입력, 라벨, 버튼 추가
        JPanel panel = new JPanel();
        JLabel addressLbl = new JLabel("주소입력");
        addressTxt = new JTextField(50);
        JButton btn = new JButton("클릭");
        panel.add(addressLbl);
        panel.add(addressTxt);
        panel.add(btn);
        //버튼을 눌렀을때 이벤트
        btn.addActionListener(e -> {
            try {
                new NaverMap(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //가운데 이미지라벨
        imageLabel = new JLabel("지도보기");
        //아래쪽 패널
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4, 1)); // 4줄
        resAddress = new JLabel("도로명주소");
        jibunAddress = new JLabel("지번주소");
        resX = new JLabel("경도");
        resY = new JLabel("위도");
        panel2.add(resAddress);
        panel2.add(jibunAddress);
        panel2.add(resX);
        panel2.add(resY);
        
        //레이아웃 설정
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH); //위의 패널을 윈도우창에 붙임.
        add(imageLabel, BorderLayout.CENTER); //이미지라벨
        add(panel2, BorderLayout.SOUTH); //패널2를 아래쪽에 붙임

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //윈도우 창 X 닫기
        setSize(730, 660);                   //윈도우 창 크기
        setVisible(true);                               //창 보이기
    }
}
