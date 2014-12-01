/*
 * This class is demonstrate a simple socket server, no matter what content read from request,
 * server will write back a standard HTTP response.
 * This is a block IO server, subsequence request will block until current request finish.
 */
package com.tool.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan_zhu
 */
public class MyBIOServer {
    public static final String SAMPLE_HTML_RESPONSE = "HTTP/1.1 200 OK\r\n"+
                                                      "Cache-Control:max-age=0\r\n"+
                             "Connection:Keep-Alive\r\n"
                             +"Content-Type:text/html; charset=utf-8\r\n"
                             +"Date:Tue, 18 Feb 2014 05:22:40 GMT\r\n"
                             +"Keep-Alive:timeout=5, max=99\r\n"
                             +"Server:Apache/2.2.16 (Win32) mod_auth_sspi/1.0.4 mod_perl/2.0.4 Perl/v5.10.0\r\n"
                             +"Set-Cookie:FOSWIKISID=f5c43ac7a23d4addd754f7e3c1fccd44; path=/; HttpOnly\r\n"
                                                      +"\r\n"
                                                      +"<html>test</html>\r\n";
    public static void main(String[]args){
        
        try {
            ServerSocket ss = new ServerSocket();
            SocketAddress sa = new InetSocketAddress("localhost",8080);
            ss.bind(sa);
            byte [] readbuff = new byte[1024];
            System.out.println("server started.");
            while (true){
                 Socket s =ss.accept();
                 if (s.getInputStream().read(readbuff)!=-1){
                     System.out.println(new String(readbuff));
                 };
                 
                 String response = 
                         SAMPLE_HTML_RESPONSE
                         ;
                 s.getOutputStream().write(response.getBytes());
                 s.close();
//                 s.close();
            }
           
        } catch (IOException ex) {
            Logger.getLogger(MyBIOServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
