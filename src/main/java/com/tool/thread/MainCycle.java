package com.tool.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by kunrong on 14-6-15.
 */
public class MainCycle {
    public static void main(String[] args){
        CyclicBarrier cb = new CyclicBarrier(3, new CycleSubTask());

    }

}
