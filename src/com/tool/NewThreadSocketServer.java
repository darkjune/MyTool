package com.tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14-3-7
 * Time: 下午10:31
 * To change this template use File | Settings | File Templates.
 */
public class NewThreadSocketServer {
    Logger log = getLogger(NewThreadSocketServer.class.getName());
    ServerSocket server = null;
    AtomicInteger count = new AtomicInteger(1);

    public NewThreadSocketServer() throws IOException, InterruptedException {
        server = new ServerSocket(8080, 1);
        System.out.println(" Server start... listen on:8080" + server.getInetAddress().toString());
    }

    public void service() throws InterruptedException {
        while (true) {
            try {
                log.info("wait connection...");
                Socket socket = server.accept();
                SocketHandler sh = new SocketHandler(socket);
                Thread thread = new Thread(sh);
                thread.setName("HandlerThread-"+ count.getAndIncrement());

                thread.start();
            } catch (IOException e) {
                log.severe(e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    class SocketHandler implements Runnable {
        Socket socket;

        public SocketHandler(Socket socket) {
            this.socket = socket;
        }

        private SocketHandler() {

        }

        public void run() {
            log.info(socket.toString());
            InputStream is = null;
            try {
                is = socket.getInputStream();

                Scanner scan = new Scanner(is);

                while (scan.hasNextLine()) {
                    String str = scan.nextLine();
                    System.out.println("Read from "+socket.getRemoteSocketAddress()+"-->"+str);
                    if(str.equals("bye")) break;
                    socket.getOutputStream().write(str.getBytes());
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }  finally {
                if (socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }

    }


    public static void main(String[] args) {
        try {
            NewThreadSocketServer ss = new NewThreadSocketServer();
            ss.service();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

