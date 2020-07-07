package com.athome;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理模式复习
 * 
 * @author admin
 *
 */
interface ProductService {
	public void getProductInfo();
}

class ProductServiceImp implements ProductService {

	@Override
	public void getProductInfo() {
		System.out.println("获取到产品信息为小鸡");
	}

}

/**
 * 静态代理
 * 
 * @author admin
 *
 */
class StaticProxyClass implements ProductService {
	private ProductService productService;

	public StaticProxyClass(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public void getProductInfo() {
		check();
		productService.getProductInfo();
	}

	public void check() {
		System.out.println("通过静态代理模式进行校验");
	}

}

/**
 * 动态代理(JDK代理)
 * @author admin
 *
 */
class DymicProxyClass implements InvocationHandler {

	// 目标代理类
	private ProductService productService;

	public DymicProxyClass(ProductService productService) {
		this.productService = productService;
	}

	// 代理方法
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		check();
		return method.invoke(productService, args);
	}

	public void check() {
		System.out.println("通过动态代理模式进行校验");
	}

}

/**
 * 通过cglib代理
 * @author admin
 *
 */
class CGLIBClass implements MethodInterceptor{
	
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		 check();
		return methodProxy.invokeSuper(proxy, args);
	}
	
	public void check() {
		System.out.println("通过cglib代理模式进行校验");
	}
}

public class ProxyDemo {

	@Test
	public void staticProxyTest() {
		ProductService productService1 = new StaticProxyClass(new ProductServiceImp());
		productService1.getProductInfo();
	}

	@Test
	public void dymicProxyTest() {
		ProductService productService = new ProductServiceImp();
		ProductService productService2 = (ProductService) Proxy.newProxyInstance(productService.getClass().getClassLoader(), productService.getClass().getInterfaces(),
				new DymicProxyClass(productService));
		productService2.getProductInfo();
	}
	
	@Test
	public void cglibProxyTest(){
		ProductService productService = new ProductServiceImp();
		ProductService productService3 = (ProductService) Enhancer.create(productService.getClass(), new CGLIBClass());
		productService3.getProductInfo();
	}
	
	
}
