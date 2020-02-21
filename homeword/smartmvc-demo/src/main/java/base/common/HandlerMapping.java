package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * ӳ�䴦������
 * 	 �����ṩ����·���봦�����Ķ�Ӧ��ϵ��
 *  ����:  "/hello.doӦ����HelloController��hello����������
 *
 */
public class HandlerMapping {
	
	/*
	 * maps���������·���봦�����Ķ�Ӧ��ϵ��
	 *   Handler��װ�˴�����ʵ������Ӧ�ķ�������(Method)��
	 */
	private Map<String,Handler> maps = 
			new HashMap<String,Handler>();
	/**
	 * ��������·��("����/hello.do")���ض�Ӧ��Handler����
	 * @param path ����·��
	 */
	public Handler getHandler(String path) {
		return maps.get(path);
	}
	
	/**
	 * ����������·���봦�����Ķ�Ӧ��ϵ��
	 *    ����list���ϣ���ÿһ��������ʵ��������java������ƶ�ȡ���ڷ���ǰ��
	 *    @RequestMappingע���е�����·����Ȼ��������·����Ϊkey,�Դ�����
	 *    ʵ������������ķ�װ(��Handler����)��Ϊvalue,����Ӧ��ϵ��ӵ�
	 *    maps���档
	 * @param beans : ������ʵ����ɵļ��ϡ�
	 */
	public void process(List beans) {
		System.out.println("HandlerMapping's process()");
		for(Object obj : beans) {
			
			//path1Ϊ����·��
			String path1 = "";
			//��ü��ڴ�������ǰ��@RequestMappingע��
			RequestMapping rm1 = obj.getClass().getAnnotation(RequestMapping.class);
			if(rm1 != null) {
				path1 = rm1.value();
			}
			
			//��ô����������з���
			Method[] methods = obj.getClass().getDeclaredMethods();
			//�������з���
			for(Method mh : methods) {
				//��ü��ڷ���ǰ��@RequestMappingע��
				RequestMapping rm = mh.getAnnotation(RequestMapping.class);
				//�������·��
				String path = rm.value();
				System.out.println("path:" + path);
				//��������ʵ���ͷ�������(Method)��װ��Handler�������棬��������java�������ȥ���ô������ķ���
				Handler handler = new Handler();
				handler.setMh(mh);
				handler.setObj(obj);
				//������·���봦�����Ķ�Ӧ��ϵ��ӵ�maps����
				maps.put(path1 + path, handler);
			}
			
		}
		System.out.println("maps:" + maps);
		
	}
	

}




