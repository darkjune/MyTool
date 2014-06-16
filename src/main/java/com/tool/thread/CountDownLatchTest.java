/*
 * CountDownLatch ?????????????????countDown?? ????await???????
 * 
 */
package com.tool.thread;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan_zhu
 */
public class CountDownLatchTest {
    CountDownLatch latch = new CountDownLatch(4);
     
    public void doMain() throws InterruptedException{
        
            for(int i=1;i<5;i++){
            Task task = new Task(latch);
            Thread t1 = new Thread(task);
            t1.setName("Thread-" + i);
            t1.start();
        }
        Thread t2 = new Thread(new WaitTask(latch));
        t2.start();
        latch.await();
        System.out.println(Thread.currentThread().getName()+" thread(t1) start.");
    }
    public static void main(String[] args){
        CountDownLatchTest test = new CountDownLatchTest();
        try {
            test.doMain();
        } catch (InterruptedException ex) {
            Logger.getLogger(CountDownLatchTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
    

class Task implements Runnable{
    CountDownLatch tlatch;

    Task(CountDownLatch latch) {
        this.tlatch = latch;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() +" do something.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
        tlatch.countDown();
        System.out.println(Thread.currentThread().getName() +" count.");
    }
    
}

class WaitTask implements Runnable{
    CountDownLatch latch ;

    public WaitTask(CountDownLatch cdl){
        this.latch = cdl;
    }

    public void run() {
        try {
            latch.await();
            System.out.println("Second WaitThread(t2) start.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
