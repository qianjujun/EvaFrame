package com.hello7890.lib;



public class MyClass {
    public static void main(String[] args) {
        System.out.println(MyClass.class.getName().hashCode());


        Cat cat = new RedHat();
        System.out.println(cat.getClass().getName());
    }
}
