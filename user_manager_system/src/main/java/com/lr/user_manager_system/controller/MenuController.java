package com.lr.user_manager_system.controller;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.service.MenuService;
import com.lr.user_manager_system.service.RoleMenuService;
import com.lr.user_manager_system.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

   @Autowired
   MenuService menuService;
   @Autowired
   RoleMenuService roleMenuService;

   //查询权限
   @RequiresPermissions("权限查找")
    @GetMapping("/Menu/findMenu")
    public List<Menu> getAllRole(){

        return menuService.getAllMenu();
    }

    @RequiresPermissions("权限添加")
    @PostMapping("/Menu/addMenu")
    public Result addMenu(@RequestBody Menu menu){
        try{
            if (menu.getPid()!=0){
                Integer pid=menu.getPid();
                //添加子菜单时更新叶子节点
                menuService.updateStateById(pid);
            }
           menu.setState("叶节点");
           menuService.addMenu(menu);
           return new Result(200,"添加成功");
        }catch (Exception e){
            System.out.println(e);
            return new Result(400,"添加失败");
        }
    }

    //根据pID查询权限
    @RequiresPermissions("查找子权限")
    @GetMapping("/Menu/find_by_pid")
    public List<Menu> findByPid(Integer pid){
        List<Menu> menu = menuService.selectByPid(pid);
        return menu;
    }


    //根据ID查询权限
    @GetMapping("/Menu/find_by_id")
    public Menu findById(Integer id){
        Menu menu = menuService.selectById(id);
        return menu;
    }
    //更新用户
    @RequiresPermissions("权限修改")
    @PutMapping("/Menu/update")
    public Result updateMenu(@RequestBody  Menu menu){
        try{

            menuService.update(menu);
            return new Result(200,"修改成功");
        }catch (Exception e) {
            System.out.println("出错啦：" + e);
            return new Result(400,"修改失败");
        }
    }

    //删除菜单
    @RequiresPermissions("权限删除")
    @DeleteMapping("/Menu/delete")
    public Result deleteUser(Integer id,String state){
        Menu Mymenu = menuService.selectById(id);
        try{
            //先删除菜单角色表中菜单对应的所有权限，
            roleMenuService.deleteByMenu(id);
            if(state.equals("根节点")){
                //menuService.updateState(id);
                //查询出父类为id的所有子类,然后直接删除父类
                List<Menu> menus = menuService.selectByPid(id);
               for (Menu menu:menus){
                   //循环删除子类
                   deleteUser(menu.getId(),menu.getState());
               }
               menuService.delete(id);
               if( Mymenu.getPid()!= 0) {
//                   System.out.println("查找父类++++++++++++++++++++++++++++++");
                   //再根据当前类的PID查找父类，判断父类中是否还有子类，如果没有改成叶节点
                   Menu ParentMenu = menuService.selectById(Mymenu.getPid());
                   List<Menu> menus2 = menuService.selectByPid(ParentMenu.getId());
                   if (menus2.isEmpty()) {
                       menuService.updateMyStateById(ParentMenu.getId(), "叶节点");
                   }
               }
            }else{
                System.out.println("删除");
                menuService.delete(id);
                if( Mymenu.getPid()!= 0) {
                    //再判断父类中是否还有子类，如果没有改成叶节点
                    Menu ParentMenu = menuService.selectById(Mymenu.getPid());
                    List<Menu> menus2 = menuService.selectByPid(ParentMenu.getId());
                    System.out.println("是否为空" + menus2.equals(null));
                    if (menus2.isEmpty()) {
                        menuService.updateMyStateById(ParentMenu.getId(), "叶节点");
                    }
                }
            }
            return new Result(200,"成功");
        }catch (Exception e){
            System.out.println(e);
            return new Result(400,"删除失败");
        }

    }

}
