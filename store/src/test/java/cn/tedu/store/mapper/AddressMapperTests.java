package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

	
	@Autowired
	private AddressMapper addressMapper;
	
	@Test
	public void addressByUid() {
		List<Address>list=addressMapper.findByUid(20);
		for (Address address : list) {
			System.err.println(address);
		}
	}
	
	
	@Test
	public void updateDefaultByAid() {
		Integer rows=addressMapper.updateDefaultByAid(20, "zt", new Date());
		System.err.println("rows="+rows);
	}
	
	
	@Test
	public void updateNonDefaultByUid() {
		Integer rows=addressMapper.updateNonDefaultByUid(20);
		System.err.println("rows="+rows);
	}
	
	
	@Test
	public void findByAid() {
		Address a=addressMapper.findByAid(20);
		System.err.println("address="+a);
	}
	
	
	@Test
	public void deleteByAid() {
		addressMapper.deleteByAid(3);
		System.err.println("OK");
	}
	
	@Test
	public void findLastModifiedByUid() {
		Address a=addressMapper.findLastModifiedByUid(20);
		System.err.println("OK--"+a);
	}
	
	
	
	
	
}
