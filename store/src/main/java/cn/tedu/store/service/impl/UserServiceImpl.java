package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.UserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;

/**
 * 实现类
 * @author 原来你是光啊！
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void reg(User user) {
		//输出日志
		System.err.println("UserServiceImp.reg()");
		System.err.println("\t注册数据："+user);

		//从参数中获取用户名
		String name=user.getUsername();
		//调用userMapper的findByUsername方法执行查询
		User result=userMapper.findByUsername(name);
		//判断查询结果是否不为null
		if(result!=null) {
			//是：结果不为null，表示用户名已被占用，则抛出UsernameDuplicateException
			throw new UsernameDuplicateException("注册失败！您尝试注册的用户名"+name+"已被占用！");
		}

		//准备注册
		//补全数据：加密盐值
		String salt=UUID.randomUUID().toString();
		user.setSalt(salt);
		String md5Password=getMd5Password(user.getPassword(), salt);
		user.setPassword(md5Password);
		//补全数据：isDelete()：值为0
		user.setIsDelete(0);
		//补全数据：4项日志
		Date now=new Date();
		user.setCreatedUser(name);
		user.setCreatedTime(now);
		user.setModifiedUser(name);
		user.setModifiedTime(now);	
		System.err.println("\t插入数据："+user);
		//调用insert执行注册
		Integer rows=userMapper.insert(user);
		//判断受影响的行数是否不为1
		if(rows!=1) {
			//是：抛出InsertException
			throw new InsertException("注册失败！插入数据时出现未知错误！请联系系统管理员！");
		}	
	}

	@Override
	public User login(String username, String password) {
		//输出日志
		System.err.println("\tUserServiceImpl.login()");
		System.err.println("\tusername:"+username);
		System.err.println("\tpassword:"+password);

		//根据参数username，调用findByUsername()方法执行查询
		User result=userMapper.findByUsername(username);
		//判断查询结果是否为null
		if(result==null) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("登录失败！找不到匹配的用户名！");
		}

		//判断查询结果中的isDelete是否为1(已删除)
		if(result.getIsDelete()==1) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("登陆失败！该用户已被注销！");

		}

		// 从查询结果中取出盐值
		String salt = result.getSalt();
		System.err.println("\t盐值："+salt);
		// 基于参数password与盐值执行加密，得到密文
		String md5Password = getMd5Password(password, salt);
		// 判断以上得到的密文与查询结果中的password是否不一致
		System.err.println("\t数据库中的密码：" + result.getPassword());
		System.err.println(md5Password+System.lineSeparator()+result.getPassword());
		if (!md5Password.equals(result.getPassword())) {
			// 是：抛出PasswordNotMatchException
			throw new PasswordNotMatchException("登录失败！密码错误！");
		}

		//创建新的User对象
		User user=new User();
		//将查询结果中的uid，username，password封装到新创建的对象中
		user.setUid(result.getUid());
		user.setUsername(result.getUsername());
		user.setAvatar(result.getAvatar());
		//返回新创建的对象
		return user;
	}

	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword, String username) {
		//日志：输出原密码和新密码
		System.err.println("changePassword()");
		System.err.println("\t原密码："+oldPassword);
		System.err.println("\t新密码："+newPassword);
		//根據參數uid對於userMapper的findByUid()查詢用戶數據
		User result=userMapper.findByUid(uid);
		//判斷查詢結果是否為null
		if(result==null) {
			//是：UserNotFoundException
			throw new UserNotFoundException();
		}

		//判斷查詢結果中的isDelete是否為1
		if(result.getIsDelete()==1) {
			//是：UserNotFoundException
			throw new UserNotFoundException();
		}

		//從查詢結果中取出盐值
		String salt=result.getSalt();
		//日志：将原密码执行加密
		System.err.println("\t将原密码执行加密");
		//將參數oldPassword結合鹽值執行加密，得到oldMd5Password
		String oldMd5Password=getMd5Password(oldPassword, salt);
		System.err.println("\toldMd5Password:"+oldMd5Password);
		//日志：输出数据库中的密码
		System.err.println("\t数据库中的密码："+result.getPassword());
		//判斷oldMd5Password與查詢結果中password是否不一致
		if(!oldMd5Password.equals(result.getPassword())) {
			//是：PasswordNotMatchException
			throw new PasswordNotMatchException();
		}

		//日志：将新密码执行加密
		//將參數newPassword結合鹽值執行加密，得到newMd5Password
		String newMd5Password=getMd5Password(newPassword, salt);
		System.err.println("\t将新密码执行加密："+newMd5Password);
		//調用userMapper的updatePasswordByUid()執行更新密碼，并獲得返回的受影響行數
		Integer rows=userMapper.updatePasswordByUid(uid, newMd5Password, username,new Date());
		System.err.println("rows:"+rows);
		//判斷受影響行數是否不爲1
		if(rows!=1) {
			//是：UpdateException
			throw new UpdateException();
		}
	}

	@Override
	public User showInfo(Integer uid) {
		//根據參數uid查詢用戶數據
		User result=userMapper.findByUid(uid);
		//判断查询结果是否为null
		if(result==null) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("获取用户信息失败，用户数据不存在");
		}

		//判断查询结果中的isDelete是否为1
		if(result.getIsDelete()==1) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("获取用户信息失败，用户数据不存在");
		}

		//创建新的User对象
		User user=new User();
		//将查询结果中的username，phone，email，gender封装到新对象
		user.setUsername(result.getUsername());
		user.setPhone(result.getPhone());
		user.setEmail(result.getEmail());
		user.setGender(result.getGender());
		//返回新User对象
		return user;
	}

	@Override
	public void changeInfo(Integer uid, String username, User user) {
		//根據參數uid查詢用戶數據
		User result=userMapper.findByUid(uid);
		//判断查询结果是否为null
		if(result==null) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户信息失败，用户数据不存在");
		}

		//判断查询结果中的isDelete是否为1
		if(result.getIsDelete()==1) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户信息失败，用户数据不存在");
		}

		//向参数user中封装uid
		user.setUid(result.getUid());
		//向参数user中封装username到modifiedUser
		user.setModifiedUser(result.getModifiedUser());
		//向参数user中封装modifiedTime
		user.setModifiedTime(result.getModifiedTime());
		//调用持久层userMapper的updateInfoByUid
		Integer rows=userMapper.updateUserByUid(user);
		//判断手影响的行数是否不为1
		if(rows!=1) {
			throw new UpdateException();

		}

	}
	
	
	@Override
	public void changeAvatar(Integer uid, String avatar, String username) {
	    // 基于参数uid调用userMapper的findByUid()查询用户数据
	    User result = userMapper.findByUid(uid);
	    // 判断查询结果是否为null
	    if (result == null) {
	        // 是：抛出UserNotFoundException
	        throw new UserNotFoundException(
	            "修改用户头像失败！用户数据不存在！");
	    }

	    // 判断查询结果中的isDelete是否为1
	    if (result.getIsDelete() == 1) {
	        // 是：抛出UserNotFoundException
	        throw new UserNotFoundException(
	            "修改用户头像失败！用户数据不存在！");
	    }

	    // 调用userMapper的updateAvatarByUid()执行更新密码，并获取返回的受影响的行数
	    Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
	    // 判断受影响的行数是否不为1
	    if (rows != 1) {
	        // 是：UpdateException
	        throw new UpdateException(
	            "修改用户头像失败！执行更新用户头像时出现未知错误！请联系系统管理员！");
	    }
	}
	

	/**
	 * 盐值加密方法
	 * @param password 原始密码
	 * @param salt 盐值
	 * @return 加密后的密码
	 */
	private String getMd5Password(String password,String salt) {
		//加密规则：使用 盐+密码+盐 作为原文，3重加密
		System.err.println("\t加密-原始密码："+password);
		System.err.println("\t加密-盐值："+salt);
		for(int i=0;i<3;i++) {
			password=DigestUtils.md5DigestAsHex((salt+password+salt).getBytes());
		}
		System.err.println("\t加密-密文："+password);
		return password;
	}















}
