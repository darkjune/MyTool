package com.tool.thread;

import java.util.Random;

/**
 * Created by kunrong on 14-6-15.
 */
public class CycleSubTask implements Runnable{
    public void run() {
        long sleepTime = Math.round(Math.random()*8999+1000);
        System.out.println("Thread going to sleep " + sleepTime + " millseconds.");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
