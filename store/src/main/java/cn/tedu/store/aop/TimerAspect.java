package cn.tedu.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class TimerAspect {

	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object aspect(ProceedingJoinPoint pjp) throws Throwable {
		//开始时间
		long start=System.currentTimeMillis();
		
		//相当于执行了service中的注册或登录或其他方法
		//调用的proceed()会抛出异常，必须继续抛出
		Object result=pjp.proceed();
		
		//结束时间
		long end=System.currentTimeMillis();
		
		System.err.println("耗时："+(end-start)+"ms");
		return result;
	}
	
	
	
	
	
	
	
	
	
}
