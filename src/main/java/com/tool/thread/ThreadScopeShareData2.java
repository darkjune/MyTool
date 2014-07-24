/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.thread;

import java.util.Random;

/**
 * @author John_Cheng
 * @date Jul 9, 2014
 * 
 */
public class ThreadScopeShareData2 {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					int data = new Random().nextInt();
					println(Thread.currentThread().getName() + " has put count:" + data);
					MyThreadScopeData.getInstance().setName("name" + data);
					MyThreadScopeData.getInstance().setAge(data);
					println(Thread.currentThread().getName() + " (-->MyThreadScopeData): "
							+ MyThreadScopeData.getInstance());
					new A().get();
					new B().get();
				}

			}).start();
		}
	}

	static class A {
		public void get() {
			// println("A " + Thread.currentThread().getName() + " has get count:"
			// +threadData.get(Thread.currentThread()));
			// println("A " + Thread.currentThread().getName() + " has get count:" +threadLocal.get());
			MyThreadScopeData data = MyThreadScopeData.getInstance();
			println("A " + Thread.currentThread().getName() + " Singlton: " + data);
			println("A " + Thread.currentThread().getName() + " has get count:" + data.getName());
		}
	}

	static class B {
		public void get() {
			println("B " + Thread.currentThread().getName() + " Singlton: "
					+ MyThreadScopeData.getInstance());
			println("B " + Thread.currentThread().getName() + " has get count:"
					+ MyThreadScopeData.getInstance().getName());
		}
	}

	static class MyThreadScopeData {
		private String name;
		private int age;

		private MyThreadScopeData() {
		}

		private static MyThreadScopeData instance;

		public static MyThreadScopeData getInstance() {
			/* MyThreadScopeData22 */instance = map.get();
			if (instance == null) {
				instance = new MyThreadScopeData();
				map.set(instance);
			}
			return instance;
		}

		public static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
			println("setName:" + Thread.currentThread().getName() + " name=" + name + " this.name=" + this.name + " " + this.toString());
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
			println("setAge:" + Thread.currentThread().getName() + " " + this.toString());
		}

		@Override
		public String toString() {
			String instStr = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
			return instStr + "{name=" + name + ",age=" + age + "}";
		}
		
	}
	
	static void println(String str) {
		System.out.println(System.nanoTime() + " " + str);
	}
}
