/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool;

/**
 *
 * @author ryan_zhu
 */
public class PerformanceTest {

    private static long i;
    private volatile static long vt;
    private static final int TEST_SIZE = 10000000;

    public static void main(String[] args) {
        long time = System.nanoTime();
        for (int n = 0; n < TEST_SIZE; n++) {
            vt = System.currentTimeMillis();
        }
        System.out.println("volatile write:");
        System.out.println((System.nanoTime() - time));

        time = System.nanoTime();
        for (int n = 0; n < TEST_SIZE; n++) {
            i = System.currentTimeMillis();
        }
        System.out.println("normal write:");
        System.out.println((System.nanoTime() - time));

        for (int n = 0; n < TEST_SIZE; n++) {
            synchronized (PerformanceTest.class) {
            }
        }
        System.out.println("sync .class block time:");
        System.out.println(-time + (time = System.nanoTime()));

        for (int n = 0; n < TEST_SIZE; n++) {
            vt++;
        }
        System.out.println("voltaile ++ time:");
        System.out.println(-time + (time = System.nanoTime()));

        for (int n = 0; n < TEST_SIZE; n++) {
            vt = i;
        }
        System.out.println(-time + (time = System.nanoTime()));

        for (int n = 0; n < TEST_SIZE; n++) {
            i = vt;
        }
        System.out.println(-time + (time = System.nanoTime()));

        for (int n = 0; n < TEST_SIZE; n++) {
            i++;
        }
        System.out.println(-time + (time = System.nanoTime()));

        for (int n = 0; n < TEST_SIZE; n++) {
            i = n;
        }
        System.out.println(-time + (time = System.nanoTime()));
    }
}
