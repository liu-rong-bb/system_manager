package com.lr.user_manager_system.service.Impl;

import com.lr.user_manager_system.bean.Users;
import com.lr.user_manager_system.controller.pagehelper.BannerBean;
import com.lr.user_manager_system.mapper.UserMapper;
import com.lr.user_manager_system.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserImpl implements UserService {

	@Resource
	UserMapper userMapper;

	@Override
	public List<Users> getAllUser(){
		return userMapper.selectAllUser();
	}

	@Override
	public void addUser(Users user){
		userMapper.insertUser(user);
	}

	@Cacheable(cacheNames = "Users" ,key = "#id")
	@Override
	public Users selectById(Integer id) {
		return userMapper.selectById(id);
	}

//	@CachePut(cacheNames = "Users" ,key = "#Users.id")   加上这个缓存就会报ID为null的错
	@Override
	public void updatae(Users user) {
		userMapper.updatae(user);
	}

	//将缓存都删除
	@CacheEvict(cacheNames = "Users" ,allEntries = true)
	@Override
	public void delete(Integer id) {
		userMapper.delete(id);
	}

	@Override
	public String fingPassworld(String name){
		return userMapper.fingPassworld(name);
	}

	public Users selectUserByUsername(String username){
		return userMapper.selectUserByUsername(username);
	}

	@Override
	public void addegister(Users user) {
		userMapper.addegister(user);
	}
}
