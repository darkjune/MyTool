package com.tool;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14-3-1
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class TestFinal {
   public static final String word = "";
//    word = "f" ;

    public static void main(String[] args){
        ThreeStooges test = new ThreeStooges();
        System.out.println(test.isStooge("Mos"));
        test.changeStooges();
        System.out.println(test.isStooge("Test1"));
    }
}
