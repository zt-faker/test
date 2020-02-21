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
			//��¼�ɹ�
			return "redirect:login/toWelcome.do";
		}else {
			//��¼ʧ��
			request.setAttribute("login_failed", "�û������������");
			return "login";
		}
		
	}
	
	@RequestMapping("/toWelcome.do")
	public String toWel() {
		System.out.println("LoginController's toWel()");
		return "welcome";
	}
	
	
}














