/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.rpc;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan_zhu
 */
public class RMIServiceMain {
    public static void main(String[] args){
        try {
            IRMIService service = new RMIServiceImpl();
            //LocateRegistry.createRegistry(1099);  //register port
            //Naming.rebind("rmi://127.0.0.1:1099/RMIService", service);  //register URL
            Naming.rebind("RMIService", service);
            System.out.println("RMI start!");
        } catch (RemoteException ex) {
            Logger.getLogger(RMIServiceMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RMIServiceMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
