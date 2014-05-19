/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author ryan_zhu
 */
public class Server implements Runnable{
 
    private int port = 80;
    private Selector selector;
 
    public Server() {
 
    }
 
    @Override
    public void run() {
 
        try {
            selector = Selector.open();
 
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.register(selector, SelectionKey.OP_ACCEPT);
 
            System.out.println("start server");
 
            while(true){
 
                int n = selector.select();
 
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> it = set.iterator();
 
                while (it.hasNext()) {
 
                    SelectionKey key = it.next();
                    it.remove();
 
                    if (key.isAcceptable()) {
//                        Session session = new Session(key);
                        System.out.println("access");
                    } else if (key.isReadable()) {
                        key.interestOps(0);
//                        Session session = (Session) key.attachment();
//                        AnalyseHandle analyseHandle = new AnalyseHandle(session);
//                        ThreadPool.getInstance().execute(analyseHandle);
                    }
 
                }
 
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args){
        new Thread(new Server()).start();
    }
}
