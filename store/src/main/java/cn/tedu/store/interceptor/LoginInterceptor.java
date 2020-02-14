package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
/**
 * 登录拦截器
 * @author 原来你是光啊！
 *
 */
public class LoginInterceptor  implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)throws Exception {
		if(request.getSession().getAttribute("uid")==null) {
			response.sendRedirect("/web/login.html");
			return false;
		}
		return true;
	}
	
	
	
	
	
}
