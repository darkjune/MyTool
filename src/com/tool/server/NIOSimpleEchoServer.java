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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ryan_zhu
 */
public class NIOSimpleEchoServer implements Runnable {

    ServerSocketChannel ssc;
    Selector selector;
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Map<SocketChannel, byte[]> messages = new HashMap<SocketChannel, byte[]>();

    public void NIOServer() throws IOException {
    }

    @Override
    public void run() {
        try {
            ssc = ServerSocketChannel.open();
            selector = Selector.open();
            ssc.socket().bind(new InetSocketAddress(8080));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int n = selector.select(); //block when no event.
                if (n > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            System.out.println("access:" + key);


                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            SocketChannel serverChannel = (SocketChannel) key.channel();
                            buffer.clear();
                            int i = serverChannel.read(buffer);
                            if (i > 0) {
                                buffer.flip();
                                byte[] buf = Arrays.copyOfRange(buffer.array(), 0, i);
                                messages.put(serverChannel, buf);
                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                                System.out.println("read from:" + serverChannel.socket().getRemoteSocketAddress()
                                        + "\nmessage:\n" + new String(buf));

                            } else if (i == -1) {
                                key.cancel();
                            }

                        } else if (key.isWritable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            byte[] buf = messages.get(channel);
                            if (buf != null) {
                                messages.remove(channel);
                                key.interestOps(SelectionKey.OP_READ);
                                buffer.clear();
                                buffer.put(buf);
                                buffer.flip();
                                channel.write(buffer);
                                System.out.println("Write to:" + channel.socket().getRemoteSocketAddress()
                                        + " \nmessage:\n" + new String(buf));
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        System.out.println(String.format("Server start at:%s. ", date));
        Thread t = new Thread(new NIOSimpleEchoServer());
        t.setName("select main");
        t.run();
        System.out.println(String.format("Server end at:%s. ", System.currentTimeMillis()));

    }
}
