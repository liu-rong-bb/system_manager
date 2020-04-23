package com.lr.user_manager_system.service.Impl;


import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.mapper.RoleUserMapper;
import com.lr.user_manager_system.service.RoleUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class RoleUserImpl implements RoleUserService {

    @Resource
    RoleUserMapper roleUserMapper;

    public String getRole(String LoginName){
        return roleUserMapper.getRole(LoginName);
    }


    @Override
    public List<Users> getUserRNameB() {
        return roleUserMapper.getUserRNameB();
    }

    @Override
    public void updateByRIdAndUId(Integer userId, Integer roleId,Integer id) {
        roleUserMapper.updateByRIdAndUId(userId,roleId,id);
    }

    @Override
    public void insertByRIdAndUID(Integer RId, Integer UId) {
        roleUserMapper.insertByRIdAndiUID(RId,UId);
    }

    @Override
    public Integer seletIdByUId(Integer userId) {
        return roleUserMapper.seletIdByUId(userId);
    }

    @Override
    public void delete(Integer UserId) {
        roleUserMapper.delete(UserId);
    }

    @Override
    public Integer seletRIdByUId(Integer userId) {
        return roleUserMapper.seletRIdByUId(userId);
    }

    @Override
    public String seletRStatuByUId(Integer userId) {
        return roleUserMapper.seletRStatuByUId(userId);
    }

    @Override
    public List<Integer> seletIdByRId(Integer roleId) {
        return roleUserMapper.seletIdByRId(roleId);
    }

    @Override
    public void updateById(Integer RoleId , Integer id) {
        roleUserMapper.updateById(RoleId,id);
    }
}
