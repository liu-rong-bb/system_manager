package com.lr.user_manager_system.mapper;

import com.lr.user_manager_system.bean.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleUserMapper {

    //通过用户姓名查询角色名称
    @Select("select r.name from role r,role_user ru,users u " +
            "where r.id=ru.role_id and u.id = ru.user_id and u.login_name=#{loginName}")
    String getRole(String LoginName);

    //查询所有的角色名
    @Select("select u.id,u.login_name,u.name,u.sex,u.phone,u.email,u.create_time,u.update_time,u.statu,r.name rname \n" +
            "from users u,role_user ru,role r \n" +
            "where ru.user_id=u.id and ru.role_id=r.id order by u.id")
    List<Users> getUserRNameB();

    //根据用户ID更新用户角色
    @Update("update role_user set user_id=#{userId},role_id=#{roleId} where id=#{id}")
    void updateByRIdAndUId(Integer userId,Integer roleId,Integer id);

    //添加用户角色
    @Options(useGeneratedKeys = true,keyColumn ="id")//获取数据库自增ID
    @Insert("insert into role_user(role_id,user_id) values (#{RId},#{UId}) ")
    void insertByRIdAndiUID(Integer RId,Integer UId);

    @Select("select id from role_user where user_id=#{userId}")
    Integer seletIdByUId(Integer userId);

    @Delete("delete from role_user where user_id=#{UserId}")
    void delete(Integer UserId);

    @Select("select role_id from role_user where user_id=#{userId}")
    Integer seletRIdByUId(Integer userId);

    @Select("select r.statu from role r,role_user ru,users u " +
            "where r.id=ru.role_id and u.id = ru.user_id and u.id=#{userId} ")
    String seletRStatuByUId(Integer userId);

    @Select("select id from role_user where role_id=#{roleId}")
    List<Integer> seletIdByRId(Integer roleId);

    //删除角色时，通过角色ID删除
    @Update("update role_user set role_id=#{RoleId} where id=#{id}")
    void updateById(Integer RoleId , Integer id);
}
