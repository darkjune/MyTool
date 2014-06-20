package com.tool.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Make a barrier, then start child thread generate number, after all child get number,
 * plus all.
 * Created by kunrong on 14-6-15.
 */
public class MainCycle {
    public static void main(String[] args){
        final long[] arr = new long[5];
        CyclicBarrier cb = new CyclicBarrier(3, new Runnable(){
            public void run() {
                System.out.println("All task at barrier,start custom thread.");
                long total = 0;
                for(int i=0;i<arr.length;i++){
                    total = total + arr[i];
                }
                System.out.println("Total:" + total);
            }
        });

        for(int i =1;i<4;i++){
            Thread t = new Thread(new CycleSubTask(cb,arr,i));
            t.start();
        }

        System.out.println("In main thread again.");



    }



}
