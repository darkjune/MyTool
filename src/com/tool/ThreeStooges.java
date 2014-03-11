package com.tool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14-3-1
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
@Immutable
public final class ThreeStooges {
    //建立final引用，创建后指向的对象不可更改，但对象内的数据可以更改。
    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges() {
        stooges.add("Mos");
        stooges.add("Test");

    }

    public boolean isStooge(String name){
        return stooges.contains(name);
    }
    public void changeStooges(){
        stooges.add("Test1");
    }
}
