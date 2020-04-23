package com.lr.user_manager_system.controller;

import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.mapper.UserMapper;
import com.lr.user_manager_system.service.RoleUserService;
import com.lr.user_manager_system.service.UserService;
import com.lr.user_manager_system.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleUserService roleUserService;
    @Resource
    UserMapper userMapper;


    @GetMapping("/user/findUser")
    public  List<Users> getAllUser(){
            List<Users> user = userMapper.selectAllUser();
            return user;

    }

    @RequiresPermissions("用户添加")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody Users user){
        try{
           // System.out.println(user.getName());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setStatu("有效");
            userService.addUser(user);
            //先将用户的角色绑定为普通用户
            roleUserService.insertByRIdAndUID(4,user.getId());
            return new Result(200, "注册成功！");
        }catch (Exception e){
            System.out.println(e);
            return new Result(400, "注册成功！");
        }
    }
    //更具ID查询用户
    @GetMapping("/find_by_id")
    public Users findById(Integer id){
        Users user = userService.selectById(id);
        return user;
    }
    //更新用户
    @RequiresPermissions("用户修改")
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody  Users user){
        try{
            //拿到当前用户
            String name = (String) SecurityUtils.getSubject().getPrincipal();
            String roleName = roleUserService.getRole(name);
            if(name.equals(user.getLoginName()) || roleName.equals("admin")){
                user.setUpdateTime(new Date());
                userService.updatae(user);
                return new Result(200,"成功");
            }
            return new Result(400,"不能修改其他用户");
        }catch (Exception e){
            System.out.println("出错啦："+e);
            return new Result(400,"修改失败");
        }


    }

    //删除用户
    @RequiresPermissions("用户删除")
    @DeleteMapping("delete_user")
    public Result deleteUser(Integer id,String roleName){
        try{
            //不能删除系统管理员
            if(roleName.equals("admin")) {
                return new Result(400,"不能删除系统管理员");
            }
            System.out.println("删除用户");
            //先删除用户对应的角色表中的用户数据
            roleUserService.delete(id);
            //再删除用户！！！！先后顺序
            userService.delete(id);
            System.out.println("删除用户角色成功");
            return new Result(200,"成功");
        }catch (Exception e){
            System.out.println(e);
            return new Result(400,"删除失败");
        }

    }

    //通过用户名查密码
    public String findByLoginName(String LoginName){
        return userService.fingPassworld(LoginName);
    }
}
