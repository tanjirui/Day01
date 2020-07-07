package com.athome;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
	public static void main(String[] args) {

		final Ticket1 ticket = new Ticket1();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 40; i++) {
					ticket.saleTicket();
				}
			}
		}, "AA").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 40; i++) {
					ticket.saleTicket();
				}
			}
		}, "BB").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 40; i++) {
					ticket.saleTicket();
				}
			}
		}, "CC").start();
	}
}
class Ticket1 {
		private int number = 30;// 票数，Field
		Lock lock = new ReentrantLock();

		public void saleTicket() {
			lock.lock();
			try {
				if (number > 0) {
					Thread.sleep(200);
					System.out.println(Thread.currentThread().getName() + "正在卖出第:\t" + (number--) + "\t当前还剩：" + number);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
