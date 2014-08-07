package com.tool.pattern.decorator;

/**
 * Created by kunrong on 14-7-24.
 */
public class MilkCoffee extends CoffeeDecorator{
    public MilkCoffee(Coffee c){
        CoffeeDecorator(c);
    }
    @Override
    public String getName() {
        return "milk " + coffee.getName();
    }
}
