package cn.tedu.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

	@Autowired
	private UserMapper mapper;
	@Test
	public void insert() {
		User user=new User();
		user.setUsername("root");
		user.setPassword("123");
		user.setIsDelete(0);
		Integer rows=mapper.insert(user);
		System.err.println(user);
		System.err.println(rows);
	}
	
	
	@Test
	public void updatePasswordByUid() {
		Integer uid=12;
		String password="bbb";
		String modifiedUser="zxcv";
		Date modifiedTime=new Date();
		Integer rows=mapper.updatePasswordByUid(uid, password, modifiedUser, modifiedTime);
		System.err.println(rows);
	}
	
	@Test
	public void updateAvatarByUid() {
		Integer uid=20;
		String avatar="bbbbbb";
		String modifiedUser="zxcv";
		Date modifiedTime=new Date();
		Integer rows=mapper.updateAvatarByUid(uid, avatar, modifiedUser, modifiedTime);
		System.err.println(rows);
	}
	
	@Test
	public void updateUserByUid() {
		User user=new User();
		user.setUid(20);
		user.setPhone("19929299299");
		user.setEmail("yasuo@cn.com");
		user.setGender(1);
		user.setModifiedUser("我是你爸爸");
		user.setModifiedTime(new Date());
		Integer rows=mapper.updateUserByUid(user);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void findByUid() {
		Integer uid=10;
		User result=mapper.findByUid(uid);
		System.err.println(result);
	}
	
	
	@Test
	public void findByUsername() {
		User result=mapper.findByUsername("root");
		System.err.println(result);
	}
	
	
	
	
	
	
	
	
}
