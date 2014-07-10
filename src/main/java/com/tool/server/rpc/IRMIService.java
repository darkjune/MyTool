/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author ryan_zhu
 */
public interface IRMIService extends Remote{
    public List<RMITransObj> getTransList() throws RemoteException;
}
