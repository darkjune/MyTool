package com.tool.server.rpc;

import com.tool.server.ResponseConst;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by ryan on 2014/6/8.
 */
public class SendJavaObjectClient implements Serializable{

    public String request = "test";

    public void SendJavaObjectClient(){

    }

    public static void main (String[]args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);
        ResponseConst client = new ResponseConst();
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(client);
        oos.flush();
        oos.close();

    }
}
