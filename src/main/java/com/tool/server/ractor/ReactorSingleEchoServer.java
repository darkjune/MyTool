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
                if (sc!=null){
                    new ReactorSingleHandler(selector, sc);
                }
            } catch (IOException ex) {
                Logger.getLogger(ReactorSingleEchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * Dispatch loop
     */
    @Override
    public void run() {
        try {
            selector.select();
            Set selected = selector.selectedKeys();
            Iterator i = selected.iterator();
            while(i.hasNext()){
                SelectionKey sk = (SelectionKey)i.next();
                dispatch(sk);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReactorSingleEchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void dispatch(SelectionKey selKey){
        
    }
    
    public static void main(String[] args){
        try {
            ReactorSingleEchoServer server = new ReactorSingleEchoServer(8080);
            new Thread(server).start();
        } catch (IOException ex) {
            Logger.getLogger(ReactorSingleEchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
