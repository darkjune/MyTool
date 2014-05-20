/*
 * This class is to demonstrate single thread SUN NIO channel server using, 
 * which as known a Reactor in single thread, only main thread will handle coming connection.
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author ryan_zhu
 */
public class NIOSimpleEchoServer implements Runnable {

    ServerSocketChannel ssc;
    Selector selector;
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    private Map<SocketChannel, byte[]> messages = new HashMap<SocketChannel, byte[]>();
    private AtomicInteger count = new AtomicInteger(0);

    public void NIOServer() throws IOException {
    }

    @Override
    public void run() {
        try {
            ssc = ServerSocketChannel.open();
            selector = Selector.open();
            ssc.socket().bind(new InetSocketAddress(8080));
            ssc.configureBlocking(false);

            //register selector on this channel with instrest OP.
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {

                int n = selector.select(); //block when no event.
                if (n > 0) {
                    System.out.println("\nSelector running round:" + count.incrementAndGet());
                    Set<SelectionKey> keys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            System.out.println("Handle SelectionKey.OP_ACCEPT:Key->" + key + ";\nKey.Channel->" + key.channel());

                            //handle server channel accept event.
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverChannel.accept();

                            System.out.println("new socket channel->" + socketChannel.toString()
                                    + " generate. register current selector on this channel with OP_READ.");
                            //handle remote client channel, register this selector on this channel.
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            System.out.println("Handle SelectionKey.OP_READ:Key->" + key + ";\nKey.Channel->" + key.channel());
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            buffer.clear();
                            int i = socketChannel.read(buffer);
                            if (i > 0) {
                                buffer.flip();
                                byte[] buf = Arrays.copyOfRange(buffer.array(), 0, i);
                                messages.put(socketChannel, buf);
                                key.attach(new String("1"));
                                System.out.println("read from:" + socketChannel.socket().getRemoteSocketAddress()
                                        + "\nMESSAGE:\n" + new String(buf) + "\nKey.attach->" + key.attachment());
//                                socketChannel.register(selector, SelectionKey.OP_WRITE);
                                socketChannel.register(selector, SelectionKey.OP_WRITE, new String("1"));
                                /**
                                 * 这里用interestOps取代register(Selector sel, int
                                 * ops)是因为用register(Selector sel, int
                                 * ops会导致selector中保存的SelectionKey
                                 * 被更新，原SelectionKey的attachment丢失。
                                 * 也可以用register(Selector sel, int ops,Object
                                 * att)来附上attachment，与使用interestOps等价。
                                 * 写通道90%时间是一直开放的，一般不需要注册，这里仅作示例。
                                *
                                 */
//                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                                System.out.println("\nAfter register, Key.attach->" + key.attachment());



                            } else if (i == -1) {
                                key.cancel();
                            }

                        } else if (key.isWritable()) {
                            System.out.println("Handle SelectionKey.OP_WRITE:Key->" + key + ";\nKey.Channel->" + key.channel()
                                    + "\nKey.attach->" + key.attachment());
                            SocketChannel channel = (SocketChannel) key.channel();
                            byte[] buf = messages.get(channel);
                            if (buf != null) {
                                messages.remove(channel);
                                key.interestOps(SelectionKey.OP_READ);
                                buffer.clear();
                                buffer.put(buf);
//                                buffer.put(MyBIOServer.SAMPLE_HTML_RESPONSE.getBytes());
                                buffer.flip();
                                channel.write(buffer);
                                System.out.println("Write to:" + channel.socket().getRemoteSocketAddress()
                                        + " \nMESSAGE:\n" + new String(buf));
                                channel.close();
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
        System.out.println(String.format("Server end at:%s. ", sdf.format(new Date())));

    }
}
