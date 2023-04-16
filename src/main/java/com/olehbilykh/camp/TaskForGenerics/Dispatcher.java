package com.olehbilykh.camp.TaskForGenerics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Створити два generic методи для пошуку спільних елементів у двох контейнерах різних типів (ArrayList та одновимірний масив)
 * з даними одного типу без дозволу явного наслідування.
 * В першому методі повернути Map зі спільними елементами у якості ключів та повною кількістю повторів у якості значень.
 * В другому методі додати до ArrayList всі елементи з масиву, що є відсутніми в ArrayList. Випробувати методи на об'єктах
 * одного класу (напр. Animal) із набором полів за вибором студента. Проаналізувати можливість використання створених методів
 * для передавання для передавання в них у якості параметрів контейнерів, що можуть містити об'єкти підкласів (Dog, Cat, Poodle тощо).
 * При визначенні спільних елементів використовувати тільки поля суперкласу.
 */
public class Dispatcher {
    public static void main(String[] args) {

    }
}

class Controller {
    public static Map<? extends Animal, Integer> foo1() {
        Map<? extends Animal, Integer> result = new HashMap<>();

        return result;
    }

    public List<? extends Animal> foo2() {
        List<Animal> animals = new LinkedList<>();


        return animals;
    }
}

class Animal {

}

class Dog extends Animal {

}

class Poodle extends Dog {
}

class Cat extends Animal {

}
