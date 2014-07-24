package com.tool.pattern.decorator;

/**
 * Created by kunrong on 14-7-24.
 */
public class CoffeeMake {
    public static void main(String[]args){
        CoffeeMake cm = new CoffeeMake();
        cm.doSth();
    }

    public void doSth(){
        Coffee lanshanCoffee = new LanshanCoffee();
        MilkCoffee milkCoffee = new MilkCoffee(lanshanCoffee);
        Coffee c = new IceCoffee(milkCoffee);
        System.out.println(c.getName());
    }
}
