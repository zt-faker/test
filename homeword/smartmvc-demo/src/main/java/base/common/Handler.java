package base.common;

import java.lang.reflect.Method;

/**
 * 	Ϊ�˷�������java�������ȥ���ô������ķ�������Ƶ�һ�������ࡣ
 *  
 *     mh.invoke(obj);
 */
public class Handler {
	private Method mh;
	private Object obj;
	
	public Method getMh() {
		return mh;
	}
	public void setMh(Method mh) {
		this.mh = mh;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
