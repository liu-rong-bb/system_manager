package com.lr.user_manager_system.service;

import com.lr.user_manager_system.bean.Menu;

import java.util.List;


public interface MenuService {

    List<Menu> getAllMenu();

	void addMenu(Menu menu);

    List<Menu> selectByPid(Integer pid);

    Menu selectById(Integer id);

	void update(Menu menu);

    void updateStateById(Integer id);

	void delete(Integer id);

    void updateState(Integer id);

    List<Menu> selectMenu();

    List<Menu> selectAllNotpid();

    void updateMyStateById(Integer id,String state);

}
