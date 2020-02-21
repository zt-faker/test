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
	 * 1.��ȡ�����ļ�(smartmvc.xml)�����õĴ�����������
	 * 2.��������ʵ������
	 * 3.��������ʵ������ӳ�䴦����(HandlerMapping)������
	 *    ע�� ӳ�䴦��������������·���봦�����Ķ�Ӧ��ϵ��
	 */
	public void init() throws ServletException {
		/*
		 * ��ȡ�����ļ�(smartmvc.xml)�����õĴ�����������
		 */
		//��ȡ�����ļ�����
		//getInitParameter����������GenericServlet,���ڶ�ȡ��ʼ��������
		String fileName = getInitParameter("configLocation");
		System.out.println("fileName:" + fileName);
		
		//����������
		SAXReader saxReader = new SAXReader();  
		//����һ��ָ�������ļ���������   
		InputStream in = 
				getClass().getClassLoader().getResourceAsStream(fileName);
		try {
			//���ý�������ʼ���������ļ�
			Document doc = saxReader.read(in);
			//���ҵ����ڵ�
			Element root = doc.getRootElement();
			//�ҵ����ڵ��������е��ӽڵ�
			List<Element> elements = root.elements();
			//���������ӽڵ�
			List beans = new ArrayList();
			for(Element ele : elements) {
				//��ȡ����������(��beanԪ�ص�class����ֵ)
				String className = ele.attributeValue("class");
				System.out.println("className:" + className);
				//��������ʵ����
				Object bean = Class.forName(className).newInstance();
				//��������ʵ����ӵ�beans
				beans.add(bean);
			}
			System.out.println("beans:" + beans);
			
			//����ӳ�䴦����
			handlerMapping = new HandlerMapping();
			
			//��������ʵ������ӳ�䴦����������
			handlerMapping.process(beans);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ʼ��ʧ��");
		}
		
	}
	



	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		//���������Դ·��
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);
		
		//���Ӧ����
		String contextPath = request.getContextPath();
		System.out.println("contextPath:"  + contextPath);
		
		//��������Դ·���е�Ӧ������ȡ������������·��
		String path = uri.substring(contextPath.length());
		System.out.println("path:" + path);
		
		//��������·��������HandlerMapping�����ṩ�ķ����������Ӧ��Handler����
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);
		
		if(handler != null) {
			Method mh = handler.getMh();
			Object obj = handler.getObj();
			
			Object rv = null;
			try {
				/*
				 * ���ô������ķ�����
				 * �Ȼ�ô����������Ĳ�������Ϣ��������εĻ�(Ŀǰ����汾ֻ֧��request��response),
				 * Ҫ���մ��εķ�ʽ������(mh.invoke(obj,params)��
				 */
				//��ô����������Ĳ���������Ϣ
				Class[] types = mh.getParameterTypes();
				if(types.length == 0) {
					//����������������
					rv = mh.invoke(obj);
				}else {
					//params���ڴ��ʵ�ʲ���ֵ
					Object[] params = new Object[types.length];
					//���ݲ������ͽ�����Ӧ�ĸ�ֵ
					for(int i = 0; i < types.length; i ++) {
						if(types[i] == HttpServletRequest.class) {
							params[i] = request;
						}
						if(types[i] == HttpServletResponse.class) {
							params[i] = response;
						}
					}
					//��������������
					rv = mh.invoke(obj, params);
				}
				
				//viewName:��ͼ��
				String viewName = rv.toString();
				System.out.println("viewName:" + viewName);
				/*
				 * ������ͼ����
				 * �����ͼ������"redirect:"��ͷ�����ض���
				 * ����ת����"/WEB-INF/" + ��ͼ�� + ".jsp"
				 */
				if(viewName.startsWith("redirect:")) {
					//�����ض����ַ
					String redirectPath = contextPath + "/" 
					+ viewName.substring("redirect:".length());
					//�ض���
					response.sendRedirect(redirectPath);
					
				}else {
					//����ת����ַ
					String forwardPath = "/WEB-INF/"  + viewName + ".jsp";
					//ת��
					request.getRequestDispatcher(forwardPath)
					.forward(request, response);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else {
			System.out.println("û�ж�Ӧ�Ĵ�����");
			response.sendError(404);
			return;
		}
		
		
	}
	
	
	
	
	
	

}
