package com.lr.user_manager_system.mapper;

import com.lr.user_manager_system.bean.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {

        //查询用户
        @Select("select id,name,order_num,create_time,fail_time,statu from role order by id")
        List<Role> selectAllRole();

        //增加用户
//        @Insert("insert into role (order_num,name,create_time,statu,remanks) values (" +
//                "#{orderNum},#{name},#{createTime},#{statu},#{remanks})")
//        @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")//获取数据库自增ID
        void insertRole(Role role);

        //通过ID查询用户
        @Select("select id,name,order_num,statu,remanks from role where id=#{id}")
        Role selectRoleById(Integer id);

        //更新对象
        //@Update("update role set order_num=#{orderNum},name=#{name},statu=#{statu},remanks=#{remanks} where id=#{id}")
        void updataRole(Role role);

        //删除用户
        @Delete("delete from role where id = #{id}")
        void deleteRole(Integer id);

}
