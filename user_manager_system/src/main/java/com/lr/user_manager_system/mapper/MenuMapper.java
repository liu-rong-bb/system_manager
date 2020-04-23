package com.lr.user_manager_system.mapper;


import com.lr.user_manager_system.bean.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuMapper {

        //查询菜单根据更节点
        @Select("select * from menu where pid=0")
        List<Menu> selectAllMenu();

        //增加菜单
//        @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")//获取数据库自增ID
//        @Insert("insert into menu(name,order_num,permission,pid, remanks,state,url) values (#{name},#{orderNum},#{permission}," +
//                " #{pid},#{remanks},#{state}, #{url})")
        void insertMenu(Menu menu);

        //通过父类ID查找子类
        @Select("select * from menu where pid=#{pid}")
        List<Menu> selectByPid(Integer pid);

        //通过ID查找子类
        @Select("select * from menu where id=#{id}")
        Menu selectRoleById(Integer id);


        //更新对象
//        @Update("update menu set name=#{name},order_num=#{orderNum},permission=#{permission},remanks=#{remanks},url=#{url} where id=#{id}")
        void updataMenu(Menu menu);

        //根据pid跟新state
        @Update("update menu set state='根节点' where Id=#{id}")
        void updateStateById(Integer id);


        //删除用户
        @Delete("delete from menu where id = #{id}")
        void deleteMenu(Integer id);

        @Update("update menu set state='叶节点' where Id=#{id}")
        void updateState(Integer id);

        //查询所有菜单
        @Select("select * from menu where pid=0")
        List<Menu> selectMenu();

        @Select("SELECT * FROM menu where pid!= 0")
        List<Menu> selectAllNotpid();

        @Update("update menu set state=#{state} where Id=#{id}")
        void updateMyStateById(Integer id,String state);

}
