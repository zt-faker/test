package cn.tedu.store.service;

import cn.tedu.store.entity.User;
/**
 * 处理用户数据的业务层接口
 * @author 原来你是光啊！
 *
 */
public interface UserService {
	
	/**
	 * 用户注册
	 * @param user 用户数据
	 */
	void reg(User user);
	
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录用户数据
	 */
	User login(String username,String password);	
	
	
	/**
	 * 修改密碼
	 * @param uid
	 * @param oldPassword
	 * @param newPassword
	 * @param username
	 */
	void changePassword(Integer uid,String oldPassword,String newPassword,String username);
	
	
	/**
	 * 显示当前登录的用户信息
	 * @param uid 当前登录的用户uid
	 * @return 
	 */
	User showInfo(Integer uid);
	
	
	/**
	 * 修改当前登录的用户的数据
	 * @param uid 用户id
	 * @param username 用户名
	 * @param user 修改后的用户数据
	 */
	void changeInfo(Integer uid,String username,User user);
	
	
	/**
	 * 修改头像
	 * @param uid 用户的id
	 * @param avatar 新的头像路径
	 * @param username 用户名
	 */
	void changeAvatar(Integer uid, String avatar, String username);
	
	
	
}
