package com.booktopia.www.repository;

import com.booktopia.www.domain.*;
import com.booktopia.www.domain.DTO.OrderInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    int joinInsert(UserVO uvo);

    int insertAuth(String id);

    List<AuthVO> selectAuths(String id);

    UserVO selectId(String id);

    int checkId(String id);

    String isSocialUser(String id);

    void modifyMyinfo(UserVO uvo);

    void modifyMyinfoWithPwd(UserVO uvo);

    String findId(String userName);

    void updateAddr(UserVO uvo);

    void joinInsertOauth(UserVO uvo);

    int findPwCheck(UserVO uvo);

    String findUserByUserId(String email);

    void updateUserPassword(@Param("id") String id, @Param("pw") String pw);

    int deleteMyPageUser(String id);

    List<UserVO> getList();

    void updateaddr(OrderInfoDTO oivo);

    int countAuth(String providerId);

    int modifyaddrandphone(UserVO uvo);

    void updateAccessToken(UserVO uvo);

    UserVO comusercheck(String id);

    int getTotal();

    List<UserVO> adminUserList(PagingVO pgvo);

    List<UserVO> getUserId();

    UserVO getcallinfo(String id);
}
