package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

	
	@Autowired
	private ProductService productService;
	
	@Test
	public void findHotList() {
		List<Product>list=productService.getHotList();
		for (Product product : list) {
			System.err.println("\t热销商品："+product);
		}
	}
	
	
	
}
