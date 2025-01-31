package com.booktopia.www.controller;

import com.booktopia.www.domain.*;
import com.booktopia.www.domain.DTO.CouponInfoDTO;
import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.repository.CouponMapper;
import com.booktopia.www.service.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/pay/*")
@Slf4j
public class PayController {

    @Autowired
    private SubscribeService ssv;
    @Autowired
    private PayService psv; //implements 없음
    @Autowired
    private OrderInfoService osv;
    @Autowired
    private CouponMapper couponMapper;

    @Value("2171128503337876")
    private String api;
    @Value("KiIcCNRzGYoW6U45aU1n9xI8bJ98TlUQP9tF4A1pbe44jcxQt5FxAOispEqpYa17sjNjaRojnO8GM4s6")
    private String secretkey;

    private IamportClient iamportClient = new IamportClient(api, secretkey);


    public PayController() {
        this.iamportClient = new IamportClient("2171128503337876",
                "KiIcCNRzGYoW6U45aU1n9xI8bJ98TlUQP9tF4A1pbe44jcxQt5FxAOispEqpYa17sjNjaRojnO8GM4s6");
    }

    @GetMapping("/done/{id}")
    public String done(@PathVariable("id") String id, Model m, OrderInfoDTO oidto) {
        m.addAttribute("id", id);
        return "/pay/done";
    }

    @PostMapping("/done")
    @ResponseBody
    public OrderInfoDTO postDone(@RequestBody OrderInfoDTO oidto){
        OrderInfoDTO resultOidto = osv.getSuccessPayInfo(oidto);
        return resultOidto;
    }

    @GetMapping("/getPay")
    public void getPay(Model m, @RequestParam("month") int month,@RequestParam("id")String id) {
        SubscribeInfoVO ssivo = ssv.getPayInfo(month);
        m.addAttribute("ssivo", ssivo);
        List<CouponInfoDTO> advo = couponMapper.getcoulist(id);
        m.addAttribute("advo", advo);
    }

    @PostMapping("/pay_ing/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> paymentByImpUid(@PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }


    @PostMapping("/savePayinfo")
    @ResponseBody
    public String savePayInfo(@RequestBody OrderInfoDTO oidto) {
        int isOk = osv.insertRegister(oidto);
        return isOk > 0 ? "1" : "0";
    }

    @PostMapping("/getToken")
    @ResponseBody
    public ResponseEntity<String> getToken (){

        // api url
        String url = "https://api.iamport.kr/users/getToken";

        // api에 보내는 전송 데이터
        String apiKey = "2171128503337876";
        String secretKey = "KiIcCNRzGYoW6U45aU1n9xI8bJ98TlUQP9tF4A1pbe44jcxQt5FxAOispEqpYa17sjNjaRojnO8GM4s6";
        String requestBody = "{\"imp_key\": \"" + apiKey + "\", \"imp_secret\": \"" + secretKey + "\"}";

        // api 호출을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //RestTemplate 사용하여 iamport api 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // 응답을 클라이어트에게 반환
        return response;
    }

    @PostMapping("/payInfo")
    public void payInsert(OrderInfoVO oivo){
    }

}


