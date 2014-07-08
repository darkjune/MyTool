/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Direct can Runnable.run() will not create new thread.
 * @author ryan_zhu
 */
public class RunnableStartTest {
    
    public static void main(String[] args){
        Thread.currentThread().setName("main-thread");
        System.out.println("Main thread id:"+Thread.currentThread().getId());
        RunnableStartTest test = new RunnableStartTest();
        test.start();
        
    }
    
    private class Task implements Runnable{

        @Override
        public void run() {
            Thread.currentThread().setName("child-thread");
            try {
                System.out.println("Threadid:"+Thread.currentThread().getId()+ " start.");
                Thread.sleep(5000);
                System.out.println("Threadid:"+Thread.currentThread().getId()+ " stop.");
            } catch (InterruptedException ex) {
                Logger.getLogger(RunnableStartTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void start(){
        Task task = new Task();
        task.run(); //no new thread. Main thread sleep, next child thread waiting.
        Thread t = new Thread(task);
        t.start();  //new thread comming.
        Task task1 = new Task();
        

        
        
    }
}
