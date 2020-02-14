package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;
/**
 * 处理用户数据的持久层接口
 * @author 原来你是光啊！
 *
 */
public interface UserMapper {

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数，插入成功时，可以从参数对象获取自动递增的uid值
	 */
	Integer insert(User user);
	
	/**
	 * 修改某用戶的密碼
	 * @param uid 用戶id
	 * @param password 用戶密碼
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 * @return 收影響的行數
	 */
	Integer updatePasswordByUid(
			@Param("uid")Integer uid,
			@Param("password")String password,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	/**
	 * 修改某用戶的头像
	 * @param uid 用戶id
	 * @param avatar 头像路径
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 * @return 收影響的行數
	 */
	Integer updateAvatarByUid(@Param("uid") Integer uid, @Param("avatar") String avatar,
			@Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 根据用户uid修改用户信息
	 * @param user 封装了用户uid和新的信息的对象
	 * @return 返回受影响的行数
	 */
	Integer updateUserByUid(User user);
	
	/**
	 * 根據id查詢用戶數據
	 * @param uid 用戶id
	 * @return 匹配的用戶數據，沒有則返回null
	 */
	User findByUid(Integer uid);
	
	/**
	 * 根据用户名查询用户数据详情
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);
	
	
	
	
	
}
