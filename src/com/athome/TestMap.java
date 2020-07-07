package com.athome;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class TestMap {
	public void test1() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.println("key==>" + key + "  value==>" + map.get(key));
		}
	}

	@Test
	public void test2() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			System.out.println("key==>" + next.getKey() + "  value==>" + next.getValue());
		}
	}

	@Test
	public void test3() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		for (String key : map.keySet()) {
			System.out.println("key==>" + key + "  value==>" + map.get(key));
		}
	}

	@Test
	public void test4() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("key==>" + entry.getKey() + "  value==>" + entry.getValue());
		}
	}

	@Test
	public void test5() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		map.forEach(((key, value) -> {
			System.out.println("key==>" + key + "  value==>" + value);
		}));
	}

	@Test
	public void test6() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		map.entrySet().stream().forEach((entry) -> {
			System.out.println("key==>" + entry.getKey() + "   value==>" + entry.getValue());
		});
	}

	@Test
	public void test7() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Java");
		map.put("2", "C#");
		map.put("3", "PHP");
		map.put("4", "GO");
		map.entrySet().parallelStream().forEach(entry -> {
			System.out.println("key==>" + entry.getKey() + "  value==>" + entry.getValue());
		});
	}
	
	@Test
	public void test8(){
		System.out.println("I love you");
	}
}
