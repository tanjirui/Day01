package com.athome;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;



// 主业务,转账
interface IAccountService {
	// 主业务逻辑: 转账
	void transfer();
}

class IAccountServiceImp implements IAccountService {

	@Override
	public void transfer() {
		System.out.println("调用dao层,完成转账主业务.");
	}

}


/**
 * 静态代理模式
 * @author admin
 *
 */
class StaticProxyImp implements IAccountService {
	private IAccountService IAccountService;

	public StaticProxyImp(IAccountService IAccountService) {
		this.IAccountService = IAccountService;
	}

	@Override
	public void transfer() {
		before();
		IAccountService.transfer();
	}

	/**
	 * 前置增强
	 */
	private void before() {
		System.out.println("对转账人身份进行验证.");
	}

}

/**
 * 动态代理模式(JDK代理)
 * @author admin
 *
 */
class DymicProxyImpl implements InvocationHandler{
	//目标对象
	private IAccountService IAccountService;
	public DymicProxyImpl(IAccountService IAccountService){
		this.IAccountService = IAccountService;
	}
	
	//代理方法,每次调用目标方法时都会进到这里
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		return method.invoke(IAccountService, args);
	}
	
	/**
     * 前置增强
     */
    private void before() {
        System.out.println("对转账人身份进行验证.");
    }
	
}

/**
 * CGLIB代理
 * @author admin
 *
 */
class CGLIBProxy implements MethodInterceptor{
	
	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		
		before();
		return methodProxy.invokeSuper(object, args);
	}
	/**
     * 前置增强
     */
    private void before() {
        System.out.println("对转账人身份进行验证.");
    }
}


public class SpringProxy {
	
	//静态代理
	@Test
	public void staticProxyTest(){
		IAccountService IAccountService = new StaticProxyImp(new IAccountServiceImp());
		IAccountService.transfer();
		System.out.println("静态代理");
	}

	//动态代理
	@Test
	public void dymicProxyTest(){
		//创建目标对象
        IAccountService targetService = new IAccountServiceImp();
        //创建代理对象
        IAccountService proxy =(IAccountService)Proxy.newProxyInstance(targetService.getClass().getClassLoader(), 
        		targetService.getClass().getInterfaces(), new DymicProxyImpl(targetService));
        proxy.transfer();
        System.out.println("动态代理");
	}
	
	//cglib代理
	@Test
	public void cgibTest(){
		
		//创建目标对象
        IAccountService targetService = new IAccountServiceImp();
        //创建代理对象
        IAccountService proxy = (IAccountService)Enhancer.create(targetService.getClass(), new CGLIBProxy());
        proxy.transfer();
        System.out.println("cglib代理");
	}
}
