package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTests {

	
	@Autowired
	private DistrictMapper districtMapper;
	
	@Test
	public void findByParent() {
		List<District> dis=districtMapper.findByParent("86");
		System.err.println(dis.size());
		for (District district : dis) {
			System.err.println(district);
		}
	}
	
	
	@Test
	public void ByUid() {
		String name=districtMapper.findNameByCode("110111");
		System.err.println("name:"+name);
	}
	
}
