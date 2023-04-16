package com.olehbilykh.camp.TaskForCarEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        Car bmw1 = new Car("740", "Ivan", 8000, 2000);
        Car bmw2 = new Car("740", "Ivan", 8000, 2000);
        Car kia1 = new Car("blabla", "John", 1000, 2022);
        Car kia2 = new Car("blabla", "John", 1000, 2022);
        Car kia3 = new Car("veve", "Johnathan", 100, 2013);

        Car carToFind = new Car("veve", "Johnathan", 100, 2013);

        List<Car> cars = new ArrayList<>();
        cars.add(bmw1);
        cars.add(bmw2);
        cars.add(kia1);
        cars.add(kia2);

//        checking using overridden equals, so if reference is not pointing to the same object, Collections.contains() will return false
        System.out.println(cars.contains(carToFind));
        carToFind = bmw2;
        System.out.println(cars.contains(carToFind));

        Car carToFind2 = new Car("veve", "Johnathan", 100, 2013);

//        checking using overloaded equals(Car car)
        for (Car car : cars) {
            if (car.equals(carToFind2)) {
                System.out.println(car + " equals" + carToFind2);
            }
        }
    }
}

class Car {
    private final String model;
    private final String owner;
    private final int price;
    private final int produceYear;

    public Car(String model, String owner, int price, int produceYear) {
        this.model = model;
        this.owner = owner;
        this.price = price;
        this.produceYear = produceYear;
    }

    public String getModel() {
        return model;
    }

    public String getOwner() {
        return owner;
    }

    public int getPrice() {
        return price;
    }

    public int getProduceYear() {
        return produceYear;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", owner='" + owner + '\'' +
                ", price=" + price +
                ", produceYear=" + produceYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return price == car.price && produceYear == car.produceYear && model.equals(car.model) && owner.equals(car.owner);
    }

    public boolean equals(Car car) {
        if (this == car) return true;
        if (car == null || getClass() != car.getClass()) return false;
        return price == car.price && produceYear == car.produceYear && model.equals(car.model) && owner.equals(car.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, owner, price, produceYear);
    }
}
