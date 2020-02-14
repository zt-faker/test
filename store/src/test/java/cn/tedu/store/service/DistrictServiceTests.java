package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTests {
	
	
	@Autowired
	private DistrictService districtService;
	
	@Test
	public void getByParent() {
		try {
			List<District>list=districtService.getByParent("630000");
			for (District district : list) {
				System.err.println(district);
			}
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getNameByCode() {
		try {
			String name=districtService.getNameByCode("110115");
			System.err.println(name);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
	
	
	
}
