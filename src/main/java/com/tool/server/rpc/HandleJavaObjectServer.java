package com.tool.server.rpc;

import com.tool.server.ResponseConst;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ryan on 2014/6/8.
 */
public class HandleJavaObjectServer {
    byte[] buffer = new byte[1024];
    ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
    byte[] data ;

    public void handleJavaObjectServer() throws Exception{
        ServerSocket server = new ServerSocket(8080);

        while (true){
            Socket socket = server.accept();
            while (socket.getInputStream().available()>0){
//                int count = socket.getInputStream().read(buffer);
//                System.out.println(count +" bytes are reading.");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ResponseConst soc = (ResponseConst)ois.readObject();
//                System.out.println("ResponseConst.= " + soc.request);
            }
            socket.close();

        }
    }

    public static void main(String[] args) {
        HandleJavaObjectServer server = new HandleJavaObjectServer();
        try {
            server.handleJavaObjectServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
