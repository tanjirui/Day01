package com.athome;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 复习卖票的case，同步复习
 * 三个售票员卖票.
 * 
 * @author admin
 *
 */
public class Thread01 {
	public static void main(String[] args) {
		final Ticket ticket = new Ticket();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i < 10; i++) {
					ticket.saleTicket();
				}
			}
		}, "AA").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i < 15; i++) {
					ticket.saleTicket();
				}
			}
		}, "BB").start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i < 20; i++) {
					ticket.saleTicket();
				}
			}
		}, "CC").start();
	}
}

class Ticket {
	private int number = 30;
	Lock lock = new ReentrantLock();

	public void saleTicket() {

		lock.lock();
		try {
			if (number > 0) {
				Thread.sleep(200);
				System.out.println(Thread.currentThread().getName() + "正在卖第" + number-- + "张票,还剩下:" + number + "张票");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}
}
