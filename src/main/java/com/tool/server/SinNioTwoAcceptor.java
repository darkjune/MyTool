/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan_zhu
 */
public class SinNioTwoAcceptor {

    ServerSocketChannel ssc;

    public void start() throws Exception {
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(true);
        SocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
        ssc.bind(address);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Acceptor(0));
        executor.execute(new Acceptor(1));
    }

    private class Acceptor implements Runnable {

        public Acceptor(int i) {
            String tName = Thread.currentThread().getName() + "-Acceptor" + i;
            Thread.currentThread().setName(tName);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " start waiting.");
                    SocketChannel sc = ssc.accept();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    sc.write(buf.put(("test".getBytes())));
                    sc.close();
                    System.out.println(Thread.currentThread().getName() + " reading.");
                }

            } catch (IOException ex) {
                Logger.getLogger(SinNioTwoAcceptor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        SinNioTwoAcceptor server = new SinNioTwoAcceptor();
        server.start();
    }
}
