package com.lr.user_manager_system.controller;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.service.MenuService;
import com.lr.user_manager_system.service.RoleMenuService;
import com.lr.user_manager_system.service.RoleUserService;
import com.lr.user_manager_system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//实现前端的动态菜单
@RestController
public class UserRoleMenuController {
    @Autowired
    UserService userService;
    @Autowired
    RoleUserService roleUserService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    MenuService menuService;

    /**
     * 利用 shiro 获取当前登录用户的 id
     * 根据用户 id 查询出该用户对应所有角色的 id
     * 根据这些角色的 id，查询出所有可访问的菜单项
     *
     */

    @RequiresPermissions("用户管理")
    @GetMapping("/index/menu")
    public List<Menu> getMenusByCurrentUser() {
        String loginName = SecurityUtils.getSubject().getPrincipal().toString();
        //String  loginName = "liu";
        Users user = userService.selectUserByUsername(loginName);
        //通过用户ID查询角色ID
        Integer roleId = roleUserService.seletIdByUId(user.getId());
        List<Menu> menus = new ArrayList<>();
        List<Integer> mIds = roleMenuService.selectMenuIdByRID(roleId);

        for (Integer mId : mIds) {
            System.out.println("rm.getMenu_id():" + mId + "+++++++++++++++++++");
            Menu menu = menuService.selectById(mId);
            // 防止多角色状态下菜单重复
            if(!menus.contains(menu)) {
                menus.add(menu);
            }
        }
        handleMenus(menus);
        return menus;
    }

    /**
     * 遍历菜单项，根据每一项的 id 查询该项出所有的子项，并放进 children 属性
     * 剔除掉所有子项，只保留第一层的父项。
     * @param menus
     */

    public void handleMenus(List<Menu> menus) {
        for (Menu menu : menus) {
            List<Menu> children = menuService.selectByPid(menu.getId());
            menu.setChildren(children);
        }

        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            if (menu.getPid() != 0) {
                iterator.remove();
            }
        }
    }

}
