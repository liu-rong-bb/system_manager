package com.lr.user_manager_system.service;



import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.controller.pagehelper.BannerBean;
import org.apache.ibatis.lang.UsesJava7;

import java.util.List;

public interface UserService{

    List<Users> getAllUser();

	void addUser(Users user);

	Users selectById(Integer id);

	void updatae(Users user);

	void delete(Integer id);

	String fingPassworld(String name);

	Users selectUserByUsername(String username);

	void addegister(Users user);

}
