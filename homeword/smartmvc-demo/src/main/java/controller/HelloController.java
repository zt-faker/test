package controller;

import base.annotation.RequestMapping;

/**
 *  ��������
 *  ����ҵ���߼��Ĵ�����Ȼ��Ҳ����ȥ���������������ҵ���߼���
 *		
 *   
 */
@RequestMapping("/demo")
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println("HelloController's hello()");
		/*
		 * ������ͼ����
		 * DispatcherServletĬ�ϻ�ת����"/WEB-INF/" + ��ͼ����+ ".jsp"
		 */
		return "hello";
	}
}



