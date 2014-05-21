/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.ractor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 *
 * @author ryan_zhu
 */
public class RactorEchoServer implements Runnable{
    
    public void RactorEchoServer(int port) throws IOException{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));
    }
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args){
        
    }
}
