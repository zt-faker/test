package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

	
	@Autowired
	private CartService cartService;
	
	@Test
	public void addToCart() {
		try {
			cartService.addToCart(2, 8, 10, "zt");
			System.err.println("OKKK");
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
	@Test
	public void getVOByUid() {
		try {
			List<CartVO>list=cartService.getVOByUid(20);
			for (CartVO cartVO : list) {
				System.err.println(cartVO);
			}
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
	
	@Test
	public void getVOByCids() {
		try {
			Integer []cids= {11,10,9,8};
			List<CartVO>list=cartService.getVOByCids(cids,20);
			for (CartVO cartVO : list) {
				System.err.println(cartVO);
			}
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
	
}
