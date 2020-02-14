package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

	
	@Autowired
	private ProductMapper productMapper;
	
	@Test
	public void findHotList() {
		List<Product>list=productMapper.findHotList();
		for (Product product : list) {
			System.err.println("\t热销商品："+product);
		}
	}
	
	
	
	@Test
	public void findById() {
		Product product=productMapper.findById(10000001);
		System.err.println(product);
	}
	
	
	
}
