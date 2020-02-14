package cn.tedu.store;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private DataSource dataSource;
	@Test
	public void getConnection() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
	
	@Test
	// MD=Message Digest
	public void md5() {
		String password="123";
		String salt=UUID.randomUUID().toString();
		for(int i=0;i<10;i++) {
			password=DigestUtils.md5DigestAsHex((password+salt).getBytes());
		}
		System.out.println(salt);
		System.out.println(password);
	}
	
	
}
