package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTests {
	
	
	@Autowired
	private AddressService addressService;
	
	@Test
	public void addnew() {
		try {
			Address address=new Address();
			address.setReceiver("我丢雷老牟");
			addressService.addnew(3, "沙雕", address);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void showAddressList() {
		try {
			List<Address>list=addressService.getByUid(20);
			for (Address address : list) {
				System.err.println(address);
			}
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
	@Test
	public void setDefault() {
		try {
			addressService.setDefault(30, 20, "我是你爸爸");
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			
			System.err.println(e.getMessage());
		}
	}
	
	
	
	@Test
	public void delete() {
		try {
			addressService.delete(30, 20, "陈平安");
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			
			System.err.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
}
