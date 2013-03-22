package org.autoswitch;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.autoswitch.complier.SimpleReturnObjectComplier;
import org.autoswitch.config.ClassMethodStatusManager;

/**
 * ���񿪹�������
 * @author weijian.zhongwj
 *
 */
public class SwitchInteceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		String classMethod = method.getClass().getName() + "." + method.getName();
		if(ClassMethodStatusManager.getInstance().isOpen(classMethod)){
			Class retClass = method.getReturnType();
			if(retClass.isAssignableFrom(Void.class)){
				return 1;
			}
			Object obj = SimpleReturnObjectComplier.getRetInstance(classMethod, retClass);	
			return obj;
		}
		return invocation.proceed();
	}

}
