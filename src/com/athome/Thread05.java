package com.athome;
/**

	1标准版,先打印苹果还是安卓？    先打印苹果
	2 增加Thread.sleep()给苹果方法，先打印苹果还是安卓？   先打印苹果
	3 增加Hello方法，先打印苹果还是Hello    先打印Hello
	4 有两部手机，先打印苹果还是安卓？  		 先打印安卓
	5 两个静态同步方法，有一部手机，先打印苹果还是安卓？   先打印苹果
	6 两个静态同步方法，有两部手机，先打印苹果还是安卓？   先打苹果
	7 一个静态同步方法，一个普通同步方法，有一部手机，先打印苹果还是安卓？    先打安卓
	8 一个静态同步方法，一个普通同步方法，有两部手机，先打印苹果还是安卓？    先打安卓
 *
 *结论:
 *一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，其它的线程都只能等待，
 *换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 *锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *加个普通方法后发现和同步锁无关
 *换成两个对象后，不是同一把锁了，情况立刻变化。
 *都换成静态同步方法后，情况又变化
 *所有的非静态同步方法用的都是同一把锁——实例对象本身，也就是说如果一个实例对象的非静态同步方法获取锁后，
 *该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
 *可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
 *所以毋须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。
 *所有的静态同步方法用的也是同一把锁——类对象本身，这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的。
 *但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，
 *而不管是同一个实例对象的静态同步方法之间，还是不同的实例对象的静态同步方法之间，只要它们同一个类的实例对象！
 *
 *
 */
public class Thread05 {
	public static void main(String[] args) {
		final Phone phone = new Phone();
		final Phone phone1 = new Phone();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				phone.getIOS();
			}
		},"AA").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				phone.getAndroid();
				//phone1.getAndroid();
				//phone.sayHello();
			}
		},"BB").start();
	}
}

class Phone{
	
	public static synchronized void getIOS(){
		try {
			Thread.sleep(4000);
			System.out.println("getIOS");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void getAndroid(){
		System.out.println("getAndroid");
	}
	
	public void sayHello(){
		System.out.println("sayHello");
	}
}
