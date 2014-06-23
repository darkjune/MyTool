/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.ractor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 *
 * @author ryan_zhu
 */
class ReactorSingleHandler implements Runnable{
    
    SocketChannel sc;
    SelectionKey sk;
    Selector selector;
    
    public ReactorSingleHandler(Selector selector, SocketChannel sc) {
        this.sc = sc;
        try {
            sc.configureBlocking(false);
            sk = sc.register(selector, 0); //?
            sk.attach(this);
            System.out.println("Handler..");
            sk.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.selector = selector;
    }

    @Override
    public void run() {

    }
    
}
