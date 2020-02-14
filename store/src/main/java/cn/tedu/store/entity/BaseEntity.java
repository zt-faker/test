package cn.tedu.store.entity;
/**
 * 实体类的父类
 * @author 原来你是光啊！
 *
 */

import java.io.Serializable;
import java.util.Date;

abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = -3122958702938259476L;
	
	private String createdUser;
	private Date createdTime;
	private String modifiedUser;
	private Date modifiedTime;
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedUser() {

		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		
		this.modifiedUser = modifiedUser;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public String toString() {
		return "BaseEntity [createdUser=" + createdUser + ", createdTime=" + createdTime + ", modifiedUser="
				+ modifiedUser + ", modifiedTime=" + modifiedTime + "]";
	}
	
	
	
	
	
}
