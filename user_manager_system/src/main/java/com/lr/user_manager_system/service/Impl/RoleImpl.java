package com.lr.user_manager_system.service.Impl;


import com.lr.user_manager_system.bean.Role;
import com.lr.user_manager_system.mapper.RoleMapper;
import com.lr.user_manager_system.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleImpl implements RoleService {

    @Resource
	RoleMapper roleMapper;

	@Override
	public List<Role> getAllRole(){
		return roleMapper.selectAllRole();
	}

	@Override
	public void addRole(Role role){
        roleMapper.insertRole(role);
    }

	@Override
	public Role selectById(Integer id){
        return roleMapper.selectRoleById(id);
    }

	@Override
	public void updatae(Role role){
        roleMapper.updataRole(role);
    }

    @CacheEvict(cacheNames = "Role" ,allEntries = true)
	@Override
	public void delete(Integer id){
        roleMapper.deleteRole(id);
    }

}
