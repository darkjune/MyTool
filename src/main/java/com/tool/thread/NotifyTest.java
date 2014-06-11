/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Notify/wait ???
 * 5???wait????????notify, ?????????int??
 * @author ryan_zhu
 */
public class NotifyTest {

    MonitorLock lock = new MonitorLock();

    public void createAndDispatch() {
        Worker w = null;
        for (int i = 1; i <= 5; i++) {
            lock.i = 5;
            w = new Worker(lock);
            Thread t = new Thread(w);
            t.setName("Thread-" + i);
            t.start();
        }

        while (lock.i > 0) {
//            System.out.println("Main thread, lock.i=" + lock.i);
            try {
                Thread.sleep(200);
                synchronized (lock) {
                    lock.notifyAll();
                    //lock.notify(); notify??????
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(NotifyTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void main(String[] args) {
        NotifyTest test = new NotifyTest();
        test.createAndDispatch();
    }
}

class MonitorLock {
    int i = 1;

    MonitorLock() {
    }
}

class Worker implements Runnable {

    MonitorLock llock;

    Worker(MonitorLock lock) {
        this.llock = lock;
    }

    public void run() {
        System.out.println("Thread" + Thread.currentThread().getName() + " start wait.");
        try {
            synchronized (llock) {
                llock.wait();
                llock.i = llock.i - 1;
                System.out.println("Thread" + Thread.currentThread().getName() + " awake.lock.i change to " +llock.i);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
