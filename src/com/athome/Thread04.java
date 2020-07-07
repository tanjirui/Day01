package com.athome;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 *  备注：多线程之间按顺序调用，实现A-B-C 三个线程启动，要求如下：
 * 
 *         AA打印5次，BB打印10次，CC打印15次 接着 AA打印5次，BB打印10次，CC打印15次 共计来20轮
 */
public class Thread04 {

	public static void main(String[] args) {
		final Resource rs = new Resource();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					rs.loopAA(i);
				}
			}
		}, "AA").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					rs.loopBB(i);
				}
			}
		}, "BB").start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					rs.loopCC(i);
				}
			}
		}, "CC").start();
	}
}

// 线程操控资源
class Resource {
	private int num = 1;
	Lock lock = new ReentrantLock();
	Condition condition1 = lock.newCondition();
	Condition condition2 = lock.newCondition();
	Condition condition3 = lock.newCondition();

	public void loopAA(int totalLoop) {
		lock.lock();
		try {
			// 1.判断是谁干活
			while (num != 1) {
				condition1.await();
			}
			// 干活
			for (int i = 1; i <= 5; i++) {
				System.out.println("当前线程为:"+Thread.currentThread().getName()+"\t第"+i+"次;进行第"+totalLoop+"轮");
			}
			num = 2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void loopBB(int totalLoop) {
		lock.lock();
		try {
			// 1.判断是谁干活
			while (num != 2) {
				condition2.await();
			}
			// 干活
			for (int i = 1; i <= 10; i++) {
				System.out.println("当前线程为:"+Thread.currentThread().getName()+"\t第"+i+"次;进行第"+totalLoop+"轮");
			}
			num = 3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void loopCC(int totalLoop) {
		lock.lock();
		try {
			// 1.判断是谁干活
			while (num != 3) {
				condition3.await();
			}
			// 干活
			for (int i = 1; i <= 15; i++) {
				System.out.println("当前线程为:"+Thread.currentThread().getName()+"\t第"+i+"次;进行第"+totalLoop+"轮");
			}
			System.out.println();
			num = 1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
