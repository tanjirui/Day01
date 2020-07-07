package com.athome;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 多线程的获取方式,实现Callable接口
 * 
 * @author admin
 *
 */
public class Thread02 {
	public static void main(String[] args) {
		FutureTask<String> futureTask = new FutureTask<>(new Test1());

		new Thread(futureTask, "AA").start();
		try {
			System.out.println(futureTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

class Test1 implements Callable<String> {

	@Override
	public String call() throws Exception {
		System.out.println("获取的多线程名称为:" + Thread.currentThread().getName());
		return "我是实现了Callable()接口的方式";
	}

}