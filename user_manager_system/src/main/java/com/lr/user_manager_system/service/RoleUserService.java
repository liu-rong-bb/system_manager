package com.lr.user_manager_system.service;

import com.lr.user_manager_system.bean.Users;

import java.util.List;

public interface RoleUserService {

    String getRole(String LoginName);

    List<Users> getUserRNameB();

    void updateByRIdAndUId(Integer userId,Integer roleId,Integer id);

    void insertByRIdAndUID(Integer RId,Integer UId);

    Integer seletIdByUId(Integer userId);

    void delete(Integer UserId);

    Integer seletRIdByUId(Integer userId);

    String seletRStatuByUId(Integer userId);

    List<Integer> seletIdByRId(Integer roleId);

    void updateById(Integer RoleId , Integer id);
}
