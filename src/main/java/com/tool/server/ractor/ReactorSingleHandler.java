/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.ractor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan_zhu
 */
class ReactorSingleHandler implements Runnable {

    SocketChannel sc;
    SelectionKey sk;
    Selector selector;
    ByteBuffer in = ByteBuffer.allocate(1024);
    ByteBuffer out = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    public ReactorSingleHandler(Selector selector, SocketChannel sc) {
        this.sc = sc;
        try {
            sc.configureBlocking(false);
            sk = sc.register(selector, 0); //?
            sk.attach(this);
            System.out.println("Handler start..");
            sk.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                write();
            }
        } catch (IOException ex) {
            Logger.getLogger(ReactorSingleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void read() throws IOException {
        
        sc.read(in);
        System.out.println("read: " + in.toString());
        if(isInputComplete()){
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
            
        }
        in.clear();
    }

    void write() throws IOException {
        sc.write(out);
        if(isOutputComplete()){
            sk.cancel();
        }
    }
    
    boolean isInputComplete() throws IOException{
        if (sc.read(in)>0) return false;
        else return true;
    }
    
    boolean isOutputComplete(){
        return false;
    }
}
