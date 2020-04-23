package com.lr.user_manager_system.service;


import com.lr.user_manager_system.bean.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRole();

	void addRole(Role role);

	Role selectById(Integer id);

	void updatae(Role role);

	void delete(Integer id);

}
