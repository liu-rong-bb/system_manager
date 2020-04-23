package com.lr.user_manager_system.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 权限表
 */
public class Menu  {

	//序号
	private Integer id;
	//菜单名称
	private String name;
	//父菜单id
	private Integer pid;
	//菜单地址
	private String url;
	//菜单节点类  0节点类  1叶子类
	private String state;
	//菜单图标
	private String icon;
	//菜单表示,对应shiro权限  update delete
	private String permission;
	//排序号，菜单ID
	@NotNull(message = "排序号不能为空")
	private Integer orderNum;
	//备注
	private String remanks;

	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	private List<Menu> children;

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getRemanks() {
		return remanks;
	}

	public void setRemanks(String remanks) {
		this.remanks = remanks;
	}
}
