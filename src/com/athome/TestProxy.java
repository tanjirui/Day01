package com.athome;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/**
 * 代理模式:
 * ①静态代理
 * ②动态代理
 * ③cglib代理
 * @author admin
 *
 */
public class TestProxy {
	public static void main(String[] args) {
		User user = new User();
		user.setId("1");
		user.setAge(10);
		user.setName("张三");
		System.out.println("----------------------以下是没有用代理模式的场景--------------------------");
		UserService userService = new UserServiceImp();
		
		//新增
		userService.add(user);
		//删除
		userService.deleteUser("1");
		//修改
		userService.updateUser("1");
		//查询
		userService.getUser("1");
		System.out.println();
		System.out.println("----------------------以下是用静态代理模式的场景--------------------------");
		/**
		 * 增加相关需求,在每个方法之前加一个安全校验check()方法
		 * 导致结果就是：
		 * 1整个系统中对应的CRUD方法都需要新增上述方法，导致修改面积过大且整个系统中处处散落着类似的方法，
		 * 试想这才一个USER类就新增4个，那200个类需要新增多少？
		 * 2.原来的代码已经稳定，新的修改导致新的测试工作扩大，根据OCP原来，对新增开启对修改关闭，
		 * 能不能想到一个好方法可以对原来的功能不破坏还加上了安全性检查?
		 * 解决:
		 * 使用代理模式
		 */
		UserService userService2 = new StaticProxy(new UserServiceImp());
		//新增
		userService2.add(user);
		//删除
		userService2.deleteUser("1");
		//修改
		userService2.updateUser("1");
		//查询
		userService2.getUser("1");
		System.out.println();
		System.out.println("----------------------以下是用动态代理模式的场景--------------------------");
		/**
		 * 针对上面静态代理的情况，已经解决了我们的需求。在不对原有代码修改的情况下，新增加了我们的安全性检查方法，完成功能。
		 * 但随之带来新的问题，一个类对应一个接口，代理类也对应实现类同样的接口，
		 * 虽然我们把散落在系统中各个方法都收集汇拢到一块了，但一个接口就要出来一个代理类导致数量又膨胀，不利于管理。
		 * 能否整个系统中就一份，大家来使用，不用一个接口一个代理类来实现，减少因为代理接口的情况而导致系统膨胀
		 * 解决:
		 * 使用动态代理模式
		 * 该例被代理的对象是User，如果明天新增Customer、Order、Staff等实体，根据需要置换需要被代理的对象即可。
		 */
		UserService userService3 = (UserService) new DymicProxyImp().
				getProxyInstance(new UserServiceImp());
		//新增
		userService3.add(user);
		//删除
		userService3.deleteUser("1");
		//修改
		userService3.updateUser("1");
		//查询
		userService3.getUser("1");
		System.out.println();
		System.out.println("----------------------以下是用cglib代理模式的场景--------------------------");
		
		/**
		 * 前面的代理均是基于接口实现的代理，现实情况中可能需要被代理的类没有实现接口，也就是说上面的方法失效。
		 * 那如何在类没有实现接口的情况下运用代理模式那?
		 */
		UserService userService4 = (UserService)new CGlibProxyImpl().
				getObjectInstance(new UserServiceImp()); 
		//新增
		userService4.add(user);
		//删除
		userService4.deleteUser("1");
		//修改
		userService4.updateUser("1");
		//查询
		userService4.getUser("1");
	}	
}

class User{
	private String id;
	private String name;
	private int age;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}

 interface UserService{
	  void add(User user);
	  void deleteUser(String id);
	  void updateUser(String id);
	  void getUser(String id);
 }

 class UserServiceImp implements UserService{

	@Override
	public void add(User user) {
		System.out.println("add用户成功...");
	}

	@Override
	public void deleteUser(String id) {
		System.out.println("delete用户成功...");
	}

	@Override
	public void updateUser(String id) {
		System.out.println("update用户成功...");
	}

	@Override
	public void getUser(String id) {
		System.out.println("get用户成功...");
	}
	 
 }
 
 /**
  * 静态代理类:原理
  * 1新增一个实现类StaticProxy，让它具备两个特点：一）实现checkSecurity方法，二）同样的实现UserService接口。
  *	2组合优于继承的原则，让StaticProxy通过UserService接口和UserServiceImpl发生组合关系，
  *  让StaticProxy代理UserServiceImpl
  * @author admin
  *
  */
 
 class StaticProxy implements UserService{
	//目标接口
	 private UserService userService;
	 //通过接口关联实现类，在构造方法里面对目标对象进行代理
	 public StaticProxy(UserService userService){
		 this.userService = userService;
	 }
	 public void check(){
		 System.out.println("使用静态代理模式,进行校验方法...");
	 }
	 
	@Override
	public void add(User user) {
		check();
		userService.add(user);
	}

	@Override
	public void deleteUser(String id) {
		check();
		userService.deleteUser(id);
	}

	@Override
	public void updateUser(String id) {
		check();	
		userService.updateUser(id);
	}

	@Override
	public void getUser(String id) {
		check();
		userService.getUser(id);
	}
	 
 }
 
 /**
  * 使用动态代理模式
  */
 class DymicProxyImp implements InvocationHandler{
	 private Object targetObject;
	 
	 public Object getProxyInstance(Object targetObject){
		 this.targetObject = targetObject;
		 return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
				 targetObject.getClass().getInterfaces(),
				 this);
	 }
	 
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		check();
		return method.invoke(targetObject, args);
	}

	//新增的方法,需要满足业务的新需求
	public void check(){
		System.out.println("采用动态代理模式-进行安全校验....");
	}
	 
 }
 
 /**
  * cglib代理模式
  * @author admin
  *
  */
 class CGlibProxyImpl  implements MethodInterceptor{
	 
	 private Object targetObject;
	 
	 public Object getObjectInstance(Object targetObject){
		 this.targetObject = targetObject;
		 
		//用于生成代理对象
		 Enhancer enhancer = new Enhancer();
		 //设置父类
		 enhancer.setSuperclass(this.targetObject.getClass());
		 //设置回调对象本身
		 enhancer.setCallback(this);
		 
		 return enhancer.create();
	 }
	 
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		check();
		return methodProxy.invoke(targetObject, args);
	}
	
	//新增的方法,需要满足业务的新需求
	public void check(){
		System.out.println("采用cglib代理模式-进行安全校验....");
	}
	
 }