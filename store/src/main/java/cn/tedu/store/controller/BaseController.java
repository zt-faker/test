package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;
/**
 * 控制器类的父类
 * 这里封装的方法都是因为在代码中会经常调用，
 * 而直接调用是会写很长的代码，
 * 所以封装成方法调用，是代码看起来更简洁
 * @author 原来你是光啊！
 *
 */
abstract class BaseController {

	//正在执行的程序和数据必须在内存中
	//static：唯一，常驻内存
	/**
	 * 响应正确时使用的状态码
	 */
	public static final int OK=2000;

	/**
	 * 从Session中获取当前登录的用户id
	 * @param session HttpSession的对象
	 * @return 当前用户的id
	 */
	protected final Integer getFromSessionUid(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}

	/**
	 * 从Session中获取当前登录用户的用户名
	 * @param session HttpSession的对象
	 * @return 当前用户名
	 */
	protected final String getFromSessionUsername(HttpSession session) {
		return session.getAttribute("username").toString();
	}
}
