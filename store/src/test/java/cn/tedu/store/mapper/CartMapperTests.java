package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

	
	@Autowired
	private CartMapper cartMapper;
	
	@Test
	public void insert() {
		Cart cart=new Cart();
		cart.setUid(2);
		cart.setPid(8);
		cart.setNum(1);
		cart.setPrice(9000L);
		Integer rows=cartMapper.insert(cart);
		System.err.println("rows="+rows);
	}
	
	@Test
	public void updateNumByCid() {
		Integer rows=cartMapper.updateNumByCid(1, 2, "zt", new Date());
		System.err.println("rows="+rows);
	}
	
	@Test
	public void findByUidAndPid() {
		Cart cart=cartMapper.findByUidAndPid(1, 5);
		System.err.println("cart="+cart);
	}
	
	@Test
	public void findVOByUid() {
		List<CartVO> list=cartMapper.findVOByUid(20);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
}
