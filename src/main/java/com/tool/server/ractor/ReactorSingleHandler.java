/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.ractor;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 *
 * @author ryan_zhu
 */
class ReactorSingleHandler implements Runnable{
    
    SocketChannel sc;
    
    Selector selector;
    
    public ReactorSingleHandler(Selector selector, SocketChannel sc) {
        this.sc = sc;
        this.selector = selector;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
