package com.booktopia.www.domain.DTO;

import com.booktopia.www.domain.OrderInfoVO;
import com.booktopia.www.domain.PayVO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO {
    //결제정보 담을 DTO
    private String impUid;
    private String merchantUid; //주문번호
    private String id;
    private String ordName;
    private String ordPhone;
    private String ordEmail;
    private String itemName;
    private int totalAmount;
    private int saleAmount;
    private int couNo;
    private String approvedAt;
    private String ordAddr;

    private String  deliStatus;
}
