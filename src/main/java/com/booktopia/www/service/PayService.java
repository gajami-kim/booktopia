package com.booktopia.www.service;

import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.repository.PayMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:secretKey.properties")
public class PayService {

    private final PayMapper payMapper;

    @Value("${imp.code}")
    private String imp_code;

    @Value("${imp.api.key}")
    private String imp_api_key;

    @Value("${imp.api.secretkey}")
    private String imp_api_secretkey;

    public String getToken() throws IOException{

        log.info("in >> {}");
        HttpURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/users/getToken");

        conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Accept","application/json");
        conn.setDoOutput(true);
        JsonObject json = new JsonObject();

        json.addProperty("imp_api_key", imp_api_key);
        json.addProperty("imp_api_secretkey",imp_api_secretkey);

        log.info(">> json >> {}", json);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        log.info(">> bw >> {}", bw);

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        log.info(">> br >> {}", br);
        Gson gson = new Gson();

        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        log.info(">> response >> {}", response);
        String token = gson.fromJson(response, Map.class).get("access_token").toString();
        log.info("! token {}", token);
        br.close();
        conn.disconnect();

        log.info("token in {}", token);
        return token;
    }



    public String paymentInfo(String impUid, String token) throws IOException, ParseException {
        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/payments/"+impUid);

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization",token);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

        JSONParser parser = new JSONParser();
        JsonObject json = (JsonObject) parser.parse(br.readLine());

        String response = json.get("response").toString();

        json = (JsonObject) parser.parse(response);

        String amount = json.get("amount").toString();

        log.info("paymentInfo amount 값 >>>>>>>>>>>>>{}", amount);

        return amount;
    }


    public void payMentCancel(String token, String impUid, String amount, String reason) throws IOException {
        log.info("payMentCancel impUid 값",impUid);

        HttpURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");

        conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Accept","application/json");
        conn.setRequestProperty("Authorization",token);

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("reason",reason);
        json.addProperty("imp_uid",impUid);
        json.addProperty("amount",amount);
        json.addProperty("checkSum",amount);

        log.info("payMentCancel imp_uid2222222 값 >>>>>>>>>>{}", impUid);
        log.info("amount",amount);
        log.info(reason);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
    }


    public void getSuccessPayInfo(OrderInfoDTO oidto) {
        payMapper.getSuccessPayInfo(oidto);
    }
}
