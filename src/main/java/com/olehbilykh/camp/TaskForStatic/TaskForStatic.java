package com.olehbilykh.camp.TaskForStatic;

/**
 * Програмне завдання. Згідно вимог шаблону MVC створити класи для моделювання
 * ремонту автомобілів двома способами: власноруч водієм та на станції
 * техобслуговування (класи Car, Driver, AutoServiceStation)
 */

public class TaskForStatic {
    public static void main(String[] args) {
        App.run();
    }
}

class App {
    public static void run() {
        Car firstCar = new Car("Max");
        Driver firstDriver = new Driver(firstCar, "Max");
        firstCar.setReadyToDrive(false);
        firstDriver.repairCar();
        firstDriver.repairCar();

        Car conceptCar = new Car("Factory");
        conceptCar.setReadyToDrive(false);
        AutoServiceStation.repairCar(conceptCar);
        AutoServiceStation.repairCar(conceptCar);

        System.out.println("Number of cars created: " + Car.numberOfCarsCreated);
    }
}

class Car {
    private String carOwner;
    private final int id;
    public boolean isReadyToDrive;
    public static int numberOfCarsCreated;

    public Car(String carOwnerName) {
        this.carOwner = carOwnerName;
        this.id = ++numberOfCarsCreated;
        this.isReadyToDrive = true;
    }

    public int getId() {
        return id;
    }

    public void setReadyToDrive(boolean readyToDrive) {
        isReadyToDrive = readyToDrive;
    }
}

class Driver {
    private String name;
    private Car ownedCar;

    public Driver(Car ownedCar, String name) {
        this.ownedCar = ownedCar;
        this.name = name;
    }

    public void repairCar() {
        if (!ownedCar.isReadyToDrive) {
            System.out.println("Car: #" + ownedCar.getId() + " is repaired by driver: " + this.name);
            ownedCar.setReadyToDrive(true);
        } else System.out.println("Car is ok, have I got nothing else to do?!");
    }
}

class AutoServiceStation {
    public static void repairCar(Car car) {
        if (!car.isReadyToDrive) {
            System.out.println("Car: #" + car.getId() + " is repaired by AutoServiceStation");
            car.setReadyToDrive(true);
        } else System.out.println("AutoServiceStation : Car is ok, you can go, but pay fo explicit diagnostic");
    }

}