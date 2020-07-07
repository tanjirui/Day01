package com.athome;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：现在两个线程，可以操作同一个变量，实现一个线程对该变量加1，一个线程对该变量减1， 实现交替，来5轮，变量初始值为零。
 * 
 * @author admin
 *
 */
public class Thread03 {

	public static void main(String[] args) {
		final Test02 Test02 = new Test02();
		final Test03 test03 = new Test03();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					try {
						Thread.sleep(200);
						Test02.increment();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "AA").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					try {
						Thread.sleep(200);
						Test02.increment();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "aa").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					try {
						Thread.sleep(300);
						Test02.minus();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "BB").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					try {
						Thread.sleep(300);
						Test02.minus();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "bb").start();

		System.out.println("===============================================================");

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <=5; i++) {
					test03.increment();
				}

			}
		}, "CC").start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <=5; i++) {
					test03.minux();
				}

			}
		}, "DD").start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <=5; i++) {
					test03.minux();
				}

			}
		}, "EE").start();
	}
}

class Test02 {
	int num = 0;
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	public void increment() {
		lock.lock();
		try {
			while (num != 0) {
				condition.await();
			}
			++num;
			System.out.println(Thread.currentThread().getName() + ":" + num);
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void minus() {
		lock.lock();
		try {
			while (num == 0) {
				condition.await();
			}
			--num;
			System.out.println(Thread.currentThread().getName() + ":" + num);
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

class Test03 {
	int num = 0;

	public synchronized void increment() {
		while (num != 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		++num;
		System.out.println("当前线程:" + Thread.currentThread().getName() + ":" + num);
		this.notifyAll();
	}

	public synchronized void minux() {
		while (num == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		--num;
		System.out.println("当前线程:" + Thread.currentThread().getName() + ":" + num);
		this.notifyAll();
	}
}
