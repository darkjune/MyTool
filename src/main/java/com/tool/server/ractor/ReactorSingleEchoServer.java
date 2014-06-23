/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.ractor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Single thread reactor pattern
 * @author ryan_zhu
 */
public class ReactorSingleEchoServer implements Runnable{
    
    Selector selector ;
    ServerSocketChannel ssc;
    
    public  ReactorSingleEchoServer(int port) throws IOException{
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }
    
    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel sc = ssc.accept();
                System.out.println(SelectionKey.OP_ACCEPT);
                System.out.println(SelectionKey.OP_READ);
                System.out.println(SelectionKey.OP_WRITE);
                System.out.println("connect:"+SelectionKey.OP_CONNECT);
                System.out.println("s&"+ (SelectionKey.OP_CONNECT&SelectionKey.OP_CONNECT));
                System.out.println("Acceptor name:" + Thread.currentThread().getName());
                if (sc!=null){
                    new ReactorSingleHandler(selector, sc);
                }
            } catch (IOException ex) {
                Logger.getLogger(ReactorSingleEchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * Dispatch loop，in a thread.
     */
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                selector.select();
                Set selected = selector.selectedKeys();  //连接过多会负载较大
                Iterator i = selected.iterator();
                while(i.hasNext()){
                    SelectionKey sk = (SelectionKey)i.next();
                    dispatch(sk);
                    selected.clear();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ReactorSingleEchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void dispatch(SelectionKey selKey){
        Runnable run = (Runnable)(selKey.attachment());
        if (run != null){
            new Thread(run, "Acceptor").start();
        }
    }

    /**
     * Entrance method, start main server thread.
     * @param args
     */
    public static void main(String[] args){
        try {
            ReactorSingleEchoServer server = new ReactorSingleEchoServer(8080);
            new Thread(server,"main server").start();
        } catch (IOException ex) {
            Logger.getLogger(ReactorSingleEchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
