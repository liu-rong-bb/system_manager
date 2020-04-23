package com.lr.user_manager_system.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 */
public class Users {
	//编号
	private Integer id;
	//用户名
	private String loginName;
	//密码
	private String passworld;

	private String name;

	private BigDecimal phone;

	private String email;

	private String sex;

	private String statu;

	private String Rname;

	private List<Role> role;

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	private Date createTime;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	private Date updateTime;

	private String remanks;

	private String salt;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRname() {
		return Rname;
	}

	public void setRname(String rname) {
		Rname = rname;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassworld() {
		return passworld;
	}

	public void setPassworld(String passworld) {
		this.passworld = passworld;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPhone() {
		return phone;
	}

	public void setPhone(BigDecimal phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemanks() {
		return remanks;
	}

	public void setRemanks(String remanks) {
		this.remanks = remanks;
	}

}
