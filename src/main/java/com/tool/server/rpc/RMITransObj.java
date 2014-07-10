/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.server.rpc;

import java.io.Serializable;

/**
 *
 * @author ryan_zhu
 */
public class RMITransObj implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
