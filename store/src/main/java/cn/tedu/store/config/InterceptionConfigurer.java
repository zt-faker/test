package cn.tedu.store.config;



import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.tedu.store.interceptor.LoginInterceptor;
/**
 * 拦截器的配置类
 * @author 原来你是光啊！
 *
 */
@Configuration
public class InterceptionConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor interceptor=new LoginInterceptor();
		List<String> patterns=new ArrayList<>();
		patterns.add("/users/reg");
		patterns.add("/users/login");
		patterns.add("/web/register.html");
		patterns.add("/web/login.html");
		patterns.add("/web/index.html");
		patterns.add("/web/product.html");
		patterns.add("/bootstrap3/**");
		patterns.add("/css/**");
		patterns.add("/images/**");
		patterns.add("/js/**");
		patterns.add("/districts/**");
		patterns.add("/products/**");
		registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
		
	}

	
	
	
	
	
	
	
	
	
}
