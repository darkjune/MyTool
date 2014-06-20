package com.tool.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Random generate number, then wait other thread reach barrier.
 * Created by kunrong on 14-6-15.
 */
public class CycleSubTask implements Runnable{
    CyclicBarrier cb ;
    long[] arr;
    int id;



    public CycleSubTask(CyclicBarrier cb, long[] arr, int id){
        this.cb = cb;
        this.arr = arr;
        this.id = id;
    }

    public void run() {
        long sleepTime = Math.round(Math.random()*8999+1000);
        arr[id] = sleepTime;

        System.out.println("Thread-" +id +": number = " + sleepTime + ".");
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Thread-" +id +" awake.");

    }
}
