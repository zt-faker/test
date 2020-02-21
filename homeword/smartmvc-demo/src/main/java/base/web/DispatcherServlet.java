package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;


public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HandlerMapping handlerMapping;
	
	@Override
	/**
	 * 1.读取配置文件(smartmvc.xml)中配置的处理器类名。
	 * 2.将处理器实例化。
	 * 3.将处理器实例交给映射处理器(HandlerMapping)来处理。
	 *    注： 映射处理器负责建立请求路径与处理器的对应关系。
	 */
	public void init() throws ServletException {
		/*
		 * 读取配置文件(smartmvc.xml)中配置的处理器类名。
		 */
		//读取配置文件名。
		//getInitParameter方法来自于GenericServlet,用于读取初始化参数。
		String fileName = getInitParameter("configLocation");
		System.out.println("fileName:" + fileName);
		
		//创建解析器
		SAXReader saxReader = new SAXReader();  
		//构造一个指向配置文件的输入流   
		InputStream in = 
				getClass().getClassLoader().getResourceAsStream(fileName);
		try {
			//利用解析器开始解析配置文件
			Document doc = saxReader.read(in);
			//先找到根节点
			Element root = doc.getRootElement();
			//找到根节点下面所有的子节点
			List<Element> elements = root.elements();
			//遍历所有子节点
			List beans = new ArrayList();
			for(Element ele : elements) {
				//读取处理器类名(即bean元素的class属性值)
				String className = ele.attributeValue("class");
				System.out.println("className:" + className);
				//将处理器实例化
				Object bean = Class.forName(className).newInstance();
				//将处理器实例添加到beans
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			
			//创建映射处理器
			handlerMapping = new HandlerMapping();
			
			//将处理器实例交给映射处理器来处理
			handlerMapping.process(beans);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("初始化失败");
		}
		
	}
	



	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		//获得请求资源路径
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);
		
		//获得应用名
		String contextPath = request.getContextPath();
		System.out.println("contextPath:"  + contextPath);
		
		//将请求资源路径中的应用名截取掉，生成请求路径
		String path = uri.substring(contextPath.length());
		System.out.println("path:" + path);
		
		//依据请求路径，调用HandlerMapping对象提供的方法来获得相应的Handler对象
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);
		
		if(handler != null) {
			Method mh = handler.getMh();
			Object obj = handler.getObj();
			
			Object rv = null;
			try {
				/*
				 * 调用处理器的方法。
				 * 先获得处理器方法的参数型信息，如果带参的话(目前这个版本只支持request和response),
				 * 要按照带参的方式来调用(mh.invoke(obj,params)。
				 */
				//获得处理器方法的参数类型信息
				Class[] types = mh.getParameterTypes();
				if(types.length == 0) {
					//处理器方法不带参
					rv = mh.invoke(obj);
				}else {
					//params用于存放实际参数值
					Object[] params = new Object[types.length];
					//依据参数类型进行相应的赋值
					for(int i = 0; i < types.length; i ++) {
						if(types[i] == HttpServletRequest.class) {
							params[i] = request;
						}
						if(types[i] == HttpServletResponse.class) {
							params[i] = response;
						}
					}
					//处理器方法带参
					rv = mh.invoke(obj, params);
				}
				
				//viewName:视图名
				String viewName = rv.toString();
				System.out.println("viewName:" + viewName);
				/*
				 * 处理视图名。
				 * 如果视图名是以"redirect:"开头，则重定向，
				 * 否则转发到"/WEB-INF/" + 视图名 + ".jsp"
				 */
				if(viewName.startsWith("redirect:")) {
					//生成重定向地址
					String redirectPath = contextPath + "/" 
					+ viewName.substring("redirect:".length());
					//重定向
					response.sendRedirect(redirectPath);
					
				}else {
					//生成转发地址
					String forwardPath = "/WEB-INF/"  + viewName + ".jsp";
					//转发
					request.getRequestDispatcher(forwardPath)
					.forward(request, response);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else {
			System.out.println("没有对应的处理器");
			response.sendError(404);
			return;
		}
		
		
	}
	
	
	
	
	
	

}
