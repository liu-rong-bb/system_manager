package com.lr.user_manager_system.service;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.bean.RoleMenu;

import java.util.List;


public interface RoleMenuService {

    public RoleMenu findById(Integer id);

    void deleteByRoleId(Integer roleId);

    RoleMenu findByRoleIdAndMenuID(Integer roleId,Integer menuId);

    List<Menu> findMenuByRoleId(Integer RoleId);

    List<Menu> findNotBaseMenuByRoleId(Integer RoleId);

    void deleteByRIdAndMId(Integer roleId,Integer menuId);

    String findMenuState(Integer menuId);

    List<RoleMenu> selectRoleMenuByRID(Integer rid);

    void insertIntoMenu(Integer RId,Integer MId);

    void deleteByRId(Integer roleId);

    void deleteByMenu(Integer menuID);

    List<Menu> findAllMenuByRoleId(Integer RoleId);

    List<Integer> selectMenuIdByRID(Integer rid);
}
