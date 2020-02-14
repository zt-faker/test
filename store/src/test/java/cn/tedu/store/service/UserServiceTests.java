package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UserNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService service;
	
	@Test
	public void reg() {
		try {
			User user=new User();
			user.setUsername("周涛");
			user.setPassword("666");
			user.setGender(1);
			user.setPhone("电话号码");
			user.setEmail("电子邮箱");
			user.setAvatar("头像");
			service.reg(user);
			System.out.println("OK"); 
			
		} catch (ServiceException e) {
			System.out.println(e.getClass().getName());
		} 
		
	}
	
	
	
	
	@Test
	public void login() {
	    try {
	        String username = "周涛";
	        String password = "666";
	        User user = service.login(username, password);
	        System.err.println("OK:" + user);
	    } catch (ServiceException e) {
	        System.err.println(e.getClass().getName());
	        System.err.println(e.getMessage());
	    }
	}
	
	
	@Test
	public void changePassword() {
		try {
			service.changePassword(18, "123", "456", "zt");
			System.err.println("OK");
		} catch (PasswordNotMatchException e) {
			System.err.println(e.getClass().getName());
		}
	}
	
	@Test
	public void showInfo() {
		try {
			User user=service.showInfo(20);
			System.err.println("OK:"+user);
		} catch (UserNotFoundException e) {
			System.err.println(e.getClass().getName());
		}
	}
	
	@Test
	public void changeInfo() {
		try {
			User user=new User();
			user.setPhone("13881817389");
			user.setEmail("zt@qq.com");
			user.setGender(0);
			service.changeInfo(20, "yasuo", user);
		} catch (UserNotFoundException e) {
			System.err.println(e.getClass().getName());
		}
	}
	
	
	@Test
	public void changeAvatar() {
	    try {
	        Integer uid = 20;
	        String avatar = "1234";
	        String username = "密码管理员";
	        service.changeAvatar(uid, avatar, username);
	        System.err.println("OK.");
	    } catch (ServiceException e) {
	        System.err.println(e.getClass().getName());
	        System.err.println(e.getMessage());
	    }
	}
	
	
	
	
	
}
