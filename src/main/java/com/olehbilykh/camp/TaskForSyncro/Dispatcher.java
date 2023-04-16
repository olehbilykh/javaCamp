package com.olehbilykh.camp.TaskForSyncro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.DoubleAccumulator;


/**
 * 1. З використанням ключового слова synchronized створити синхронну багато-
 * потокову систему для визначення загальної суми всіх наявних цілих та дробових
 * чисел в трьох текстових файлах.
 * НЕ УТВОРЮВАТИ суму всіх чисел з кожного файлу
 * окремо, потім додаючи три суми, результат треба утворювати послідовно - в міру
 * зчитування чисел з файлів.
 * НЕ ВИКОРИСТОВУВАТИ проміжне створення колекцій
 * для прочитаного тексту і не перетворювати весь файл у текст.
 * 2. Аналогічну систему створити із використанням ресурсів пакету
 * java.util.concurrent.atomic.
 */

public class Dispatcher {
    public static void main(String[] args) throws InterruptedException {
        MyCustomClass.run();
    }
}

class MyCustomClass {
    private double sum = 0;
    private final DoubleAccumulator doubleAccumulator = new DoubleAccumulator(Double::sum, 0);

    public static void run() throws InterruptedException {
        MyCustomClass myCustomClass = new MyCustomClass();

        Runnable r = () -> {
            myCustomClass.calculateNumberInFile("src/com/sigma/camp/TaskForMultithreading/inputFiles/first.txt");
            myCustomClass.calculateNumberInFile("src/com/sigma/camp/TaskForMultithreading/inputFiles/second.txt");
            myCustomClass.calculateNumberInFile("src/com/sigma/camp/TaskForMultithreading/inputFiles/third.txt");
        };
        Runnable runnableAtomic = () -> {
            myCustomClass.calculateNumberInFileAtomic("src/com/sigma/camp/TaskForMultithreading/inputFiles/first.txt");
            myCustomClass.calculateNumberInFileAtomic("src/com/sigma/camp/TaskForMultithreading/inputFiles/second.txt");
            myCustomClass.calculateNumberInFileAtomic("src/com/sigma/camp/TaskForMultithreading/inputFiles/third.txt");
        };
//        non-atomic
        long time1 = System.currentTimeMillis();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);
        Thread t4 = new Thread(r);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        long time2 = System.currentTimeMillis();

        System.out.println("Non atomic: " + myCustomClass.sum);
        System.out.println("Time: " + (time2 - time1));
//        atomic
        long time3 = System.currentTimeMillis();
        Thread t5 = new Thread(runnableAtomic);
        Thread t6 = new Thread(runnableAtomic);
        Thread t7 = new Thread(runnableAtomic);
        Thread t8 = new Thread(runnableAtomic);
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        long time4 = System.currentTimeMillis();

        System.out.println("Atomic: " + myCustomClass.doubleAccumulator.doubleValue());
        System.out.println("Time: " + (time4 - time3));
    }

    public double calculateNumberInFileAtomic(String filePath) {

        double next = 0;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    next = scanner.nextDouble();
                    doubleAccumulator.accumulate(next);
                } else {
                    scanner.next();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return doubleAccumulator.doubleValue();
    }

    public double calculateNumberInFile(String filePath) {

        double next = 0;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    next = scanner.nextDouble();
                    synchronized (this) {
                        sum += next;
                    }
                } else {
                    scanner.next();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sum;
    }
}
