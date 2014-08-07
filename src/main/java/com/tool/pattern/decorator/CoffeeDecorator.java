package com.tool.pattern.decorator;

/**
 * Created by kunrong on 14-7-24.
 */
public abstract class CoffeeDecorator implements Coffee{

    protected Coffee coffee;

    public void CoffeeDecorator(Coffee c){
        coffee = c;
    }

    private void CoffeeDecorator(){

    };


    public abstract String getName() ;


}
