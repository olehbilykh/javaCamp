package com.olehbilykh.camp.TaskForParameters;

/**
 * Програмне завдання. Створити клас-контролер з двома методами:
 * - для обчислення чотирьох математичних функцій над дробовим та цілим числами (div
 * - ділення першого операнду на другий, log10 - десятковий логарифм суми операндів,
 * log - натуральний логарифм суми операндів, pow - піднесення першого операнду у
 * степінь другого операнду), де результат буде присвоюватись першому операнду
 * (використати стандартні методи класу Math),
 * - для множення на дробову константу одного з трьох полів об’єкту класу Car, кожне з
 * яких (price, weight, speed) представлене дробовим значенням.
 * <p>
 * Першим параметром в методах передавати відповідний рядок (String), що за
 * допомогою оператора switch/case буде визначати необхідну для виконання функцію (в
 * першому методі) або необхідне поле для множення (в другому методі). Таким чином,
 * в кожний з методів необхідно передавати три параметри: для першого методу – 1)рядок
 * потрібної функції, 2)перший операнд, 3)другий операнд; для другого методу – 1)рядок,
 * що визначає потрібне поле, 2)об’єкт класу Car, 3)операнд-множник.
 */


public class Main {
    public static void main(String[] args) {
        App.run();
    }
}

class App {
    public static void run() {
        ControllerAndView.calculateMathOperation("div", 10, -23);
        ControllerAndView.changeParameterOfTheCar("speed", new Car(10, 20, 30), 2.5);
    }

}

class Car {
    private double price;
    private double weight;
    private double speed;

    public Car(double price, double weight, double speed) {
        this.price = price;
        this.weight = weight;
        this.speed = speed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

class ControllerAndView {
    public static void calculateMathOperation(String mathOperation, double firstOperand, double secondOperand) {
        switch (mathOperation) {
            case "div" -> {
                if (secondOperand == 0) {
                    System.out.println("Second operand cannot be equal to 0");
                    System.exit(0);
                }
                System.out.println(firstOperand / secondOperand);
            }
            case "log10" -> System.out.println(Math.log10(firstOperand + secondOperand));
            case "log" -> System.out.println(Math.log(firstOperand + secondOperand));
            case "pow" -> {
                System.out.println(Math.pow(firstOperand, secondOperand));
            }
            default -> {
                System.out.println("Unknown operation");
                System.exit(0);
            }
        }
    }

    public static void changeParameterOfTheCar(String ParameterToChange, Car car, double coefficient) {

        switch (ParameterToChange) {
            case "price" -> {
                car.setPrice(car.getPrice() * coefficient);
                System.out.println(ParameterToChange + " of the car is now " + car.getPrice());
            }
            case "weight" -> {
                car.setWeight(car.getWeight() * coefficient);
                System.out.println(ParameterToChange + " of the car is now " + car.getWeight());
            }
            case "speed" -> {
                car.setSpeed(car.getSpeed() * coefficient);
                System.out.println(ParameterToChange + " of the car is now " + car.getSpeed());
            }
            default -> {
                System.out.println("Unknown parameter of the car");
                System.exit(0);
            }
        }
    }
}