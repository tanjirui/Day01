package com.athome;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Thread08 {
	public static void main(String[] args) {
		//创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
		ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
		ScheduledFuture<Integer> result  = null;
		try {
			for (int i = 1; i <=10; i++) {
				result = threadPool.schedule((new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						System.out.println("当前线程为:"+Thread.currentThread().getName());
						return new Random().nextInt(20) ;
					}
				}),3,TimeUnit.SECONDS);
				System.out.println("-----------"+result.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}

}
