package com.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14-3-1
 * Time: 下午10:39
 * To change this template use File | Settings | File Templates.
 */
public class SocketClient {
    Logger log = Logger.getLogger(SocketClient.class.getName());
    ExecutorService exes = Executors.newFixedThreadPool(40);
    public void startWork(){
        for(int i=1;i<10;i++){
            exes.execute(new ClientWorker(i));
        }

    }

    class ClientWorker implements Runnable{
        int no ;
        public ClientWorker(int num){
            no=num;
        }
        @Override
        public void run() {
            try {
                Socket socket = new Socket("127.0.0.1",8080);
                log.info("client"+no+":start working.");
                socket.getOutputStream().write(new String("Client "+no).getBytes());
                byte[] buffer = new byte[1024];
                while (socket.getInputStream().read()!=-1){
                    socket.getInputStream().read(buffer);
                    System.out.println(new String(buffer));
                }
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.print("bye");
                pw.flush();
                //Thread.sleep(5000);
//                socket.close();
            } catch (Exception e) {
                log.severe(e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }

    public static void main(String[] args){
        SocketClient sc = new SocketClient();
        sc.startWork();
    }

}
