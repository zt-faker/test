package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * 映射处理器：
 * 	 负责提供请求路径与处理器的对应关系。
 *  比如:  "/hello.do应该由HelloController的hello方法来处理。
 *
 */
public class HandlerMapping {
	
	/*
	 * maps存放有请求路径与处理器的对应关系。
	 *   Handler封装了处理器实例及对应的方法对象(Method)。
	 */
	private Map<String,Handler> maps = 
			new HashMap<String,Handler>();
	/**
	 * 依据请求路径("比如/hello.do")返回对应的Handler对象。
	 * @param path 请求路径
	 */
	public Handler getHandler(String path) {
		return maps.get(path);
	}
	
	/**
	 * 负责建立请求路径与处理器的对应关系。
	 *    遍历list集合，对每一个处理器实例，利用java反射机制读取加在方法前的
	 *    @RequestMapping注解中的请求路径，然后以请求路径作为key,以处理器
	 *    实例及方法对象的封装(即Handler对象)作为value,将对应关系添加到
	 *    maps里面。
	 * @param beans : 处理器实例组成的集合。
	 */
	public void process(List beans) {
		System.out.println("HandlerMapping's process()");
		for(Object obj : beans) {
			
			//path1为请求路径
			String path1 = "";
			//获得加在处理器类前的@RequestMapping注解
			RequestMapping rm1 = obj.getClass().getAnnotation(RequestMapping.class);
			if(rm1 != null) {
				path1 = rm1.value();
			}
			
			//获得处理器的所有方法
			Method[] methods = obj.getClass().getDeclaredMethods();
			//遍历所有方法
			for(Method mh : methods) {
				//获得加在方法前的@RequestMapping注解
				RequestMapping rm = mh.getAnnotation(RequestMapping.class);
				//获得请求路径
				String path = rm.value();
				System.out.println("path:" + path);
				//将处理器实例和方法对象(Method)封装到Handler对象里面，方便利用java反射机制去调用处理器的方法
				Handler handler = new Handler();
				handler.setMh(mh);
				handler.setObj(obj);
				//将请求路径与处理器的对应关系添加到maps里面
				maps.put(path1 + path, handler);
			}
			
		}
		System.out.println("maps:" + maps);
		
	}
	

}




