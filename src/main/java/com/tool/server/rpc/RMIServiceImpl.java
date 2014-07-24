/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.rpc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan_zhu
 */
public class RMIServiceImpl extends UnicastRemoteObject  implements IRMIService{

    public RMIServiceImpl() throws RemoteException{
        
    };
    
    @Override
    public List<RMITransObj> getTransList() throws RemoteException{
        List<RMITransObj> list = new ArrayList<RMITransObj>();
        RMITransObj obj = new RMITransObj();
        obj.setName("test");
        list.add(obj);
        System.out.println("getTransList run.");
        return list;
    }
    
}
