package com.lr.user_manager_system.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.controller.pagehelper.BannerBean;
import com.lr.user_manager_system.service.RoleUserService;
import com.lr.user_manager_system.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class RoleUserController {

    @Autowired
    RoleUserService roleUserService;

    //返回全部的用户以及用户角色，并且设置分页
    //page表示当前页数，limit表示用户设置的每页显示多少条数据
    @RequiresPermissions("用户查找")
    @GetMapping("/roleUser/findUserAndRName/{page}/{limit}")
    public Result getRName(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit){
        Map<Object,Object> map = new HashMap<>();

        PageHelper.startPage(page-1, limit);//设置数据库分页查询的范围
        //分页
        Page<BannerBean> pageInfo = PageHelper.startPage(page, limit);
//        Page<Users> pageInfo = PageHelper.startPage(page, limit);
        List<Users> user = roleUserService.getUserRNameB();

        map.put("count",pageInfo.getTotal());//获取查询总条数，这里我会在后面解释
        //页中条数数
        map.put("pageSize",limit);
        map.put("data",pageInfo);
        return new Result(200,"成功",map);
    }

    //将用户重新分配角色
    @RequiresPermissions("修改角色")
    @PutMapping("/roleUser/update")
    public Result updateRoleName(Integer userId,Integer RId){
        //判断RId是否为空
        if(RId == null){
            return new Result(100);
        }
        try{
            Integer id = roleUserService.seletIdByUId(userId);
            roleUserService.updateByRIdAndUId(userId,RId,id);
            return new Result(200);
        } catch (Exception e){
            System.out.println("出错啦" + e);
            return new Result(400);
        }

    }



}
