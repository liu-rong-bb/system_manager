package com.lr.user_manager_system.service.Impl;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.bean.Role;
import com.lr.user_manager_system.bean.RoleMenu;
import com.lr.user_manager_system.mapper.RoleMenuMapper;
import com.lr.user_manager_system.service.RoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleMenuImpl implements RoleMenuService {

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Override
    public RoleMenu findById(Integer id) {
        return roleMenuMapper.findById(id);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        roleMenuMapper.deleteByRoleId(roleId);
    }

    @Override
    public RoleMenu findByRoleIdAndMenuID(Integer roleId, Integer menuId) {
        return roleMenuMapper.findByRoleIdAndMenuID(roleId,menuId);
    }

    @Override
    public List<Menu> findMenuByRoleId(Integer roleId){
        return roleMenuMapper.findMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> findNotBaseMenuByRoleId(Integer RoleId) {
        return roleMenuMapper.findNotBaseMenuByRoleId(RoleId);
    }

    @Override
    public void deleteByRIdAndMId(Integer roleId, Integer menuId) {
        roleMenuMapper.deleteByRIdAndMId(roleId,menuId);
    }

    @Override
    public String findMenuState(Integer menuId) {
        return roleMenuMapper.findMenuState(menuId);
    }

    @Override
    public List<RoleMenu> selectRoleMenuByRID(Integer rid) {
      return roleMenuMapper.selectRoleMenuByRID(rid);
    }

    @Override
    public void insertIntoMenu(Integer RId, Integer MId) {
        roleMenuMapper.insertIntoMenu(RId,MId);
    }

    @Override
    public void deleteByRId(Integer roleId) {
        roleMenuMapper.deleteByRId(roleId);
    }

    @Override
    public void deleteByMenu(Integer menuID) {
        roleMenuMapper.deleteByMenu(menuID);
    }

    @Override
    public List<Menu> findAllMenuByRoleId(Integer RoleId) {
        return roleMenuMapper.findAllMenuByRoleId(RoleId);
    }

    @Override
    public List<Integer> selectMenuIdByRID(Integer rid) {
        return roleMenuMapper.selectMenuIdByRID(rid);
    }
}
