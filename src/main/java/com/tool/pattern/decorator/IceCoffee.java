package com.tool.pattern.decorator;

/**
 * Created by kunrong on 14-7-24.
 */
public class IceCoffee extends CoffeeDecorator{

    public IceCoffee (Coffee c){
        CoffeeDecorator(c);
    }

    @Override
    public String getName() {
        return "ice " + coffee.getName();
    }
}
