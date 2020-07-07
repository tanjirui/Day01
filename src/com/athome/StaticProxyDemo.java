package com.athome;

/**
 * 静态代理类
 * 一句话：在真实主题之间，前后均可包一层，实现代理的额外拓展功能+真实主题的原有实现，
 * 目的是在不破坏原来真实主题的目标意图和功能下，对真实主题进行了功能加强和扩展
 * @author admin
 *
 */
public class StaticProxyDemo {
	public static void main(String[] args) {
		Dun dun = new ProxyDun(new people());
		dun.getMoney();
	}
}

// 债务
interface Dun {
	public void getMoney();
}

// 债主
class people implements Dun {

	@Override
	public void getMoney() {
		System.out.println("get my Money");
	}
}

// 讨债人(代理)
class ProxyDun implements Dun {
	private people people;

	public ProxyDun(people people) {
		this.people = people;
	}

	@Override
	public void getMoney() {
		System.out.println("开始讨债...");
		people.getMoney();
		System.out.println("结束讨债...");
	}

}