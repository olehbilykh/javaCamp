package com.olehbilykh.camp.TaskForParallelStream;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Із використанням паралельних потоків в масиві цілих чисел
 * знайти парні числа, з яких утворити посортований Map (key: знайдене парне число,
 * value: кількість його повторів в масиві). Порівняти часові витрати на рішення
 * завдання послідовним та паралельними потоками.
 */
public class Dispatcher {
    public static void main(String[] args) {
        int[] ints = new int[100000];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = (int) (Math.random() * 100);
        }
        long time1 = System.nanoTime();
        Arrays.stream(ints)
                .filter(i -> i % 2 == 0)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
//                .entrySet()
//                .forEach(System.out::println);
        long time2 = System.nanoTime();
        System.out.println("not parrallel " + (time2 - time1));

        long time3 = System.nanoTime();
        Arrays.stream(ints)
                .filter(i -> i % 2 == 0)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), ConcurrentSkipListMap::new, Collectors.counting()));
//                .entrySet()
//                .forEach(System.out::println);
        long time4 = System.nanoTime();
        System.out.println("parallel " + (time4 - time3));

    }
}
