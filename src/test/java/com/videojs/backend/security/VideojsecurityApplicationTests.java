package com.videojs.backend.security;

import com.videojs.backend.security.bean.SecurityBean;
import com.videojs.backend.security.service.SecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideojsecurityApplicationTests {

	@Autowired
	SecurityService securityService;

	@Test
	public void contextLoads() {
	}


	@Test
	public void testSecurityCreate(){
		try {
			String token = securityService.createToken("1","123456",10);
			System.out.println("token: "+token);
			Thread.sleep(2000);
			SecurityBean sb = securityService.decryptToken(token);
			String verify = securityService.verifySecurityBean(sb);
			System.out.println(verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
