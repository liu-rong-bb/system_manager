package com.lr.user_manager_system.controller;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.bean.Role;
import com.lr.user_manager_system.bean.RoleMenu;
import com.lr.user_manager_system.service.MenuService;
import com.lr.user_manager_system.service.RoleMenuService;
import com.lr.user_manager_system.service.RoleService;
import com.lr.user_manager_system.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleMenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    RoleService roleService;


    //查询出所有菜单
    @RequiresPermissions("查找角色菜单")
    @GetMapping("/rolemenu/getMenuTree")
    public List<Menu> getMenuTree(){
        List<Menu> menusBase = menuService.selectByPid(0);
        List<Menu> menuLNotBase = menuService.selectAllNotpid();
        for (Menu menu : menusBase) {
            List<Menu> menus = iterateMenus(menuLNotBase, menu.getId());
            menu.setChildren(menus);
        }
        return  menusBase;
    }

    /**
     *多级菜单查询方法
     */
    public List<Menu> iterateMenus(List<Menu> menuVoList,Integer pid){
        List<Menu> result = new ArrayList<Menu>();
        for (Menu menu : menuVoList) {
            //获取菜单的id
            Integer menuid = menu.getId();
            //获取菜单的父id
            Integer parentid = menu.getPid();
//            if(StringUtils.isNotBlank(parentid)){
                if(parentid.equals(pid)){
                    //递归查询当前子菜单的子菜单
                    List<Menu> iterateMenu = iterateMenus(menuVoList,menuid);
                    menu.setChildren(iterateMenu);
                    result.add(menu);
                }
//            }
        }
        return result;
    }

    //查询出所有角色的所有菜单,用于角色查找
    @RequiresPermissions("角色查找")
    @GetMapping("/rolemenu/getMenuTreeByRole")
    public List<Role> getMenuTreeByRole() {
        List<Role> role = roleService.getAllRole();
        for (Role roles : role) {
            //如果角色中的状态是无效，不遍历此角色中的菜单
            if(roles.getStatu().equals("无效")){
                continue;
            }
            List<Menu> menuBase = roleMenuService.findMenuByRoleId(roles.getId());
            List<Menu> menuLNotBase = roleMenuService.findNotBaseMenuByRoleId(roles.getId());
            for (Menu menu : menuBase) {
                List<Menu> menus = iterateMenus(menuLNotBase, menu.getId());
                menu.setChildren(menus);
            }
            roles.setChildren(menuBase);
        }
        return role;
    }

    @Transactional
    @RequiresPermissions("删除角色菜单")
    @DeleteMapping("/rolemenu/delet")
    public Result deleteByRIdAndMId(Integer roleId,Integer menuId){
        try {
            String state = menuService.selectById(menuId).getState();
            if (state.equals("根节点")) {
                //查询出父类为id的所有子类,然后直接删除父类
                List<Menu> menus = menuService.selectByPid(menuId);
                for (Menu menu : menus) {
                    //循环删除子类
                    deleteByRIdAndMId(roleId, menu.getId());
                }
                roleMenuService.deleteByRIdAndMId(roleId, menuId);
            } else {
                roleMenuService.deleteByRIdAndMId(roleId, menuId);
            }
            return new Result(200, "成功");
        }catch (Exception e){
            return new Result(400,"删除失败");
        }
    }

    @Transactional
    @RequiresPermissions("更新角色菜单")
    @PutMapping("/rolemenu/update")
    public Result roleMenuUpdate(Integer roleId, String menuIds){
        try{
            //如果为空直接返回
            if(menuIds.trim().equals("")){
                return new Result(100);
            }

            String[] idStrs = menuIds.split(",");
            //根据roleId删去之前的菜单
            List<RoleMenu> menus =  roleMenuService.selectRoleMenuByRID(roleId);
            if(!menus.equals(null)){
                roleMenuService.deleteByRoleId(roleId);
            }

            //角色菜单中遍历添加新的菜单
            for (int i=0; i < idStrs.length; i++){
                roleMenuService.insertIntoMenu(roleId,Integer.parseInt(idStrs[i]));
            }
            return new Result(200);
        } catch (Exception e){
            System.out.println("出错了++++++++++++++++" + e);
            return new Result(400);
        }
    }


}
