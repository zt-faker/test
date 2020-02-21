package controller;

import javax.servlet.http.HttpServletRequest;

import base.annotation.RequestMapping;

@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println("LoginController's toLogin()");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request) {
		System.out.println("LoginController's login()");
		
		String username = request.getParameter("username");
		System.out.println("username:" + username);
		String pwd = request.getParameter("pwd");
		
		if("Tom".equals(username) && "1234".equals(pwd)) {
			//登录成功
			return "redirect:login/toWelcome.do";
		}else {
			//登录失败
			request.setAttribute("login_failed", "用户名或密码错误");
			return "login";
		}
		
	}
	
	@RequestMapping("/toWelcome.do")
	public String toWel() {
		System.out.println("LoginController's toWel()");
		return "welcome";
	}
	
	
}














