/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 *
 * @author ryan_zhu
 */
public class NIOServer implements Runnable {

    ServerSocketChannel ssc;
    Selector selector;
    ByteBuffer buffer;

    public void NIOServer() throws IOException {
        buffer = ByteBuffer.allocate(1024);
    }

    @Override
    public void run() {
        try {
            ssc = ServerSocketChannel.open();
            selector = Selector.open();
            ssc.socket().bind(new InetSocketAddress(8080));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            String.format("Server start at :%s. ", System.currentTimeMillis());
            while (true) {
                int n = selector.select(); //block when no event.
                if (n>0){
                    Set<SelectionKey> keys = selector.selectedKeys();

                for (SelectionKey key : keys) {
                    keys.remove(key);
                    if (key.isAcceptable()) {
                        System.out.println("access:" + key);
                        
                        
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap("This is echo server \r\n".getBytes()));
                    }else if (key.isReadable()){
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        int i = socketChannel.read(buffer);
                        if (i >0){
                            System.out.println(buffer);
                        }
                        key.interestOps(0);
                        key.attachment();
                    }
                }
                }
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {

        System.out.println(String.format("Server start at:%s. ", System.currentTimeMillis()));
        new Thread(new NIOServer()).run();
        System.out.println(String.format("Server end at:%s. ", System.currentTimeMillis()));

    }
}
