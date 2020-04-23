package com.lr.user_manager_system.mapper;

import com.lr.user_manager_system.bean.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

//	@Select("select id,login_name,name,sex,phone,email,create_time,update_time,statu from users")
//	List<Users> selectAllUser();

	@Select("select * from users order by id")
	List<Users> selectAllUser();

//	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")//获取数据库自增ID
//	@Insert("insert into users (login_name,passworld,name,phone,email,sex,statu,create_time,update_time,remanks) values (\n" +
//			"        #{loginName},#{passworld},#{name},#{phone},#{email},#{sex},#{statu},#{createTime},#{createTime},#{remanks})")
 	void insertUser(Users user);

 	//通过ID查询用户
	@Select("select id,login_name,name,passworld,sex,phone,email,statu,remanks from users where id = #{id}")
 	Users selectById(Integer id);

 	//更新对象
	void updatae(Users user);

 	@Delete("delete from users where id = #{id}")
	void delete(Integer id);

 	//登录
 	@Select("select login_name from users where passworld=#{passworld} ")
	String selectLoginNameByPassworld(String passworld);

 	@Select("select passworld from users where login_name=#{loginName}")
	String fingPassworld(String loginName);

 	@Select("select * from users where login_name=#{username}")
	Users selectUserByUsername(String username);

	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")//获取数据库自增ID
 	@Insert("insert into users (login_name,passworld,statu,create_time,update_time) values (#{loginName},#{passworld},#{statu},#{createTime},#{createTime})")
	void addegister(Users user);


}

