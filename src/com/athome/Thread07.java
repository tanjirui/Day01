package com.athome;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Executors线程池获取线程
 * 
 * @author admin
 *
 */
public class Thread07 {
	public static void main(String[] args) {
		//1.创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
		//ExecutorService executorService = Executors.newFixedThreadPool(5);
		//2.创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
		//ExecutorService executorService = Executors.newSingleThreadExecutor();
		//3.创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<Integer> result = null;
		try {
			for (int i = 1; i <= 20; i++) {
				result = executorService.submit(new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						Thread.sleep(800);
						System.out.println("当前线程为:" + Thread.currentThread().getName());
						return new Random().nextInt(20);
					}
				});
				System.out.println("--------" + result.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
	}

}
