/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.thread;

/**
 *
 * @author ryan_zhu
 */
/**
 * <br>
 * do what you want to do and never stop it.
 * <br>
 */
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jack Jul 7, 2014
 * <br>
 */
public class ThreadScopeShareData {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for ( int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + " has put count:" + data);
                    MyThreadScopeData.getInstance().setName("name" + data);
                    MyThreadScopeData.getInstance().setAge(data);
                    System.out.println(Thread.currentThread().getName() + " MyThreadScopeData: " + MyThreadScopeData.getInstance());
                    
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }
    
    static class A {

        public void get() {
            //System.out.println("A " + Thread.currentThread().getName() + " has get count:" +threadData.get(Thread.currentThread()));
//			System.out.println("A " + Thread.currentThread().getName() + " has get count:" +threadLocal.get());
            MyThreadScopeData data = MyThreadScopeData.getInstance();
            System.out.println("A " + Thread.currentThread().getName() + " Singlton: " + data);
            System.out.println("A " + Thread.currentThread().getName() + " has get count:" + data.getName());
        }
    }
    
    static class B {

        public void get() {
            System.out.println("B " + Thread.currentThread().getName() + " Singlton: " + MyThreadScopeData.getInstance());
            System.out.println("B " + Thread.currentThread().getName() + " has get count:" + MyThreadScopeData.getInstance().getName());
        }
    }
    
    static class MyThreadScopeData {

        private String name;
        private int age;
        public static ThreadLocal<MyThreadScopeData> map;

        private MyThreadScopeData() {
        }
        private static MyThreadScopeData instance;
        
        public static /*synchronized*/ MyThreadScopeData getInstance() {
            /*MyThreadScopeData */            instance = map.get();
            if (instance == null) {
                instance = new MyThreadScopeData();
                map.set(instance);
            }
            return instance;
        }
        
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
