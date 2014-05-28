package com.tool;

import com.tool.server.ResponseConst;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.logging.Logger.*;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14-3-1
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSocketServer {
    Logger log = getLogger("SimpleSocketServer");
    ServerSocket server = null;
    public SimpleSocketServer() throws IOException {
        server = new  ServerSocket(8080,4);
        System.out.println("Server start... listen on:8080" + server.getInetAddress().toString());
    }
    public void service() throws InterruptedException {
        while(true)       {
            try {
                log.info("wait connection...");
                Socket socket = server.accept();
                log.info(socket.toString());
                InputStream is = socket.getInputStream();
                Scanner scan = new Scanner(is);
                byte[] buffer = new byte[1024];

//                while (scan.hasNextLine()){
//                    String str = scan.nextLine();
//                    System.out.println("Read from "+socket.getRemoteSocketAddress()+"-->"+str);
//                    if(str.equals("bye")) break;
//
//                }
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
//                socket.getOutputStream().write(new String(ResponseConst.HEAD).getBytes());
//                socket.getOutputStream().write(new String("\r\n").getBytes());
//                socket.getOutputStream().write(new String(ResponseConst.context).getBytes());
                pw.print(ResponseConst.HEAD);
                pw.print("\r\n");
                pw.print(ResponseConst.context);
                pw.flush();
//                socket.getOutputStream().flush();
                socket.setSoTimeout(5000);
//                Thread.sleep(1000);
                log.info("awake.");
                socket.close();
            } catch (IOException e) {
                log.severe(e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

     public static void main(String[] args){
         try{
             SimpleSocketServer ss = new SimpleSocketServer();
             ss.service();
         }       catch (Exception e){
             e.printStackTrace();
         }


     }

}
