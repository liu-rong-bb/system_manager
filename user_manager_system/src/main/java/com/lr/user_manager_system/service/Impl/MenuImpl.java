package com.lr.user_manager_system.service.Impl;

import com.lr.user_manager_system.bean.Menu;
import com.lr.user_manager_system.mapper.MenuMapper;
import com.lr.user_manager_system.service.MenuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuImpl implements MenuService {

    @Resource
	MenuMapper menuMapper;

	@Override
	public List<Menu> getAllMenu(){
		return menuMapper.selectAllMenu();
	}

	@Override
	public void addMenu(Menu menu){
        menuMapper.insertMenu(menu);
    }

    @Override
	public List<Menu> selectByPid(Integer pid){

		return menuMapper.selectByPid(pid);
	}

	@Override
	public Menu selectById(Integer id){
        return menuMapper.selectRoleById(id);
    }

	@Override
	public void update(Menu menu){
        menuMapper.updataMenu(menu);
    }

    @Override
	public void updateStateById(Integer id){
		menuMapper.updateStateById(id);
	}

    @CacheEvict(cacheNames = "Menu" ,allEntries = true)
	@Override
	public void delete(Integer id){
        menuMapper.deleteMenu(id);
    }

    @Override
	public void updateState(Integer id){
		menuMapper.updateState(id);
	}

	@Override
	public List<Menu> selectMenu() {
		return menuMapper.selectMenu();
	}

	@Override
	public List<Menu> selectAllNotpid() {
		return menuMapper.selectAllNotpid();
	}

	@Override
	public void updateMyStateById(Integer id, String state) {
		menuMapper.updateMyStateById(id,state);
	}
}
