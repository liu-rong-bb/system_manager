package com.lr.user_manager_system.controller;

import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.service.RoleUserService;
import com.lr.user_manager_system.service.UserService;
import com.lr.user_manager_system.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    RoleUserService roleUserService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(@RequestBody Users user,HttpSession session) {
        String loginName = user.getLoginName();
        String password = user.getPassworld();

        /* 获取Subject */
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginName, password);
        //设置cookie的存活时间
        usernamePasswordToken.setRememberMe(true);
//        UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
        try {
            subject.login(usernamePasswordToken);
            session.setAttribute("user", user);
            return new Result(200);
        } catch (Exception e){
           return new Result(400);
        }

    }

    @RequestMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result(200,"成功登出");
    }


    @PostMapping("/register")
    public Result addUser(@RequestBody Users user){
        System.out.println("user.getLoginName:"+ user.getLoginName() + "user.getPassworld():"+ user.getPassworld());
        //通过用户名查找用户
        Users user1 = userService.selectUserByUsername(user.getLoginName());
        if(user.getLoginName().trim().equals("")){
            return new Result(400,"用户名不能为空");
        } else if (user.getPassworld().trim().equals("")){
            return new Result(400,"密码不能为空");
        } else if (user1 != null) {
            return new Result(400,"用户名已经存在，请重新输入");
        } else {
            try {
                user.setCreateTime(new Date());
                user.setStatu("有效");
                userService.addegister(user);
                //先将用户的角色绑定为普通用户
                Users user2 = userService.selectUserByUsername(user.getLoginName());
                roleUserService.insertByRIdAndUID(4,user2.getId());
                return new Result(200, "注册成功！");
            } catch (Exception e) {
                return new Result(400, "注册失败");
            }
        }
    }

    @RequestMapping("/err")
    public Result erorr() {
        return new Result(200,"没有授权");
    }

    @ResponseBody
    @GetMapping(value = "/authentication")
    public String authentication(){
        return "身份认证成功";
    }

}

