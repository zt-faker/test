package base.common;

import java.lang.reflect.Method;

/**
 * 	为了方便利用java反射机制去调用处理器的方法而设计的一个辅助类。
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
