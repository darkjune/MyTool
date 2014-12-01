/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan_zhu
 */
public class RMIClient {
    public static void main(String[] args){
        try {
            IRMIService service = (IRMIService)Naming.lookup("rmi://127.0.0.1:1099/RMIService");
            List<RMITransObj> list = service.getTransList();
            System.out.println("Get RMITransObj:name->"+list.get(0).getName());
            
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
