package controller;

import base.annotation.RequestMapping;

/**
 *  处理器：
 *  负责业务逻辑的处理。当然，也可以去调用其它类来完成业务逻辑。
 *		
 *   
 */
@RequestMapping("/demo")
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println("HelloController's hello()");
		/*
		 * 返回视图名。
		 * DispatcherServlet默认会转发到"/WEB-INF/" + 视图名　+ ".jsp"
		 */
		return "hello";
	}
}



