package com.olehbilykh.camp.TaskForGC;

public class Dispatcher {
    public static void main(String[] args) {
        Car c = new Car("bmw");
        c = new Car("audi");
        c = null;
        System.gc();
    }
}

class Car {
    private String model;

    public Car(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    protected void finalize() {
        System.out.println(this.model);
    }
}