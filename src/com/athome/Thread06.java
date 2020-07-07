package com.athome;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 一个线程写,100个线程读 读读可共享; 读写,写写要互斥
 * 
 * @author admin
 *
 */
public class Thread06 {
	public static void main(String[] args) {
		final TestDemo test = new TestDemo();
		//写线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				test.write(new Random().nextInt(20));
			}
		},"AA").start();
		
		//读线程
		for (int i = 1; i <= 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					test.read();
				}
			},String.valueOf(i)).start();
		}
	}
}

class TestDemo {
	private Object obj;
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	// 写线程
	public void write(Object obj) {
		readWriteLock.writeLock().lock();
		this.obj = obj;
		try {
			System.out.println("写线程:" + Thread.currentThread().getName() + ",正在写入:" + obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	// 读线程
	public void read() {
		readWriteLock.readLock().lock();
		try {
			Thread.sleep(200);
			System.out.println("读线程:" + Thread.currentThread().getName() + ",正在读取:" + obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readWriteLock.readLock().unlock();
		}
	}
}
