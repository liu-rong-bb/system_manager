package com.lr.user_manager_system.controller;

import com.lr.user_manager_system.bean.Role;
import com.lr.user_manager_system.service.RoleMenuService;
import com.lr.user_manager_system.service.RoleService;
import com.lr.user_manager_system.service.RoleUserService;
import com.lr.user_manager_system.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    RoleUserService roleUserService;

    //用户中查找全部角色，用户查看可以分配的角色
    @RequiresPermissions("查找角色")
    @GetMapping("/role/findRole")
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }

    //添加角色
    @RequiresPermissions("角色添加")
    @PostMapping("/role/addRole")
    public Result addRole(@RequestBody Role role){
        try{
            if(role.getName().trim().equals("")){
                return new Result(400,"请输入角色名称");
            }
           // System.out.println(user.getName());
            role.setCreateTime(new Date());
            roleService.addRole(role);
            return new Result(200,"成功");
        }catch (Exception e){
            System.out.println(e);
            return new Result(400,"添加失败");
        }
    }
    //更具ID查询角色
    @RequiresPermissions("角色更新")
    @GetMapping("/role/find_by_id")
    public Role findById(Integer id){
        Role role = roleService.selectById(id);
        return role;
    }
    //更新角色
    @PutMapping("/role/update")
    public Result updateUser(@RequestBody  Role role){
        try{
            if(role.getStatu() == "无效"){
                role.setFailTime(new Date());
                //删除此角色中的所有权限
            }
            roleService.updatae(role);
            return new Result(200,"成功");
        }catch (Exception e){
            return new Result(400,"更新失败");
        }


    }

    //删除角色
    @Transactional
    @RequiresPermissions("角色删除")
    @DeleteMapping("/role/delete_user")
    public Result deleteUser(Integer id){
        try{
            //先删除角色权限表中对应的菜单
            roleMenuService.deleteByRoleId(id);
            //再更新角色用户表
            List<Integer> ids = roleUserService.seletIdByRId(id);
            for (Integer roleUserId: ids) {
                roleUserService.updateById(4,roleUserId);
            }
            //再删除角色
            roleService.delete(id);
            return new Result(200,"成功");
        }catch (Exception e){
            System.out.println(e);
            return new Result(400,"删除失败");
        }

    }

}
