package com.booktopia.www.service;

import com.booktopia.www.domain.AdCouponVO;
import com.booktopia.www.domain.DTO.CouponInfoDTO;
import com.booktopia.www.domain.DTO.OrderInfoDTO;
import com.booktopia.www.domain.QnaVO;
import com.booktopia.www.domain.UserVO;

import java.util.List;

public interface UserService {

    // 회원가입
    int joinInsert(UserVO uvo);

    int checkId(String id);

    String isSocialUser(String id);

    void modifyMyinfo(UserVO uvo);

    void modifyMyinfoWithPwd(UserVO uvo);

    String findId(String userName);

    int findPwCheck(UserVO uvo);

    int deleteMyPageUser(String id);

    List<OrderInfoDTO> getPlist(String id);

    OrderInfoDTO getsublist(String id);

    int modifyaddrandphone(UserVO uvo);

    List<CouponInfoDTO> getcouList(String id);


    UserVO getcallinfo(String id);
}
