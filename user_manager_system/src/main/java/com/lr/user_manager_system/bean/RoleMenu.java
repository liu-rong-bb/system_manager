package com.lr.user_manager_system.bean;

/**
 * 角色权限表
 */
public class RoleMenu {

    private Integer id;

    private Integer role_id;

    private Integer menu_id;

    public RoleMenu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }
}
