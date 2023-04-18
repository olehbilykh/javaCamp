package com.olehbilykh.camp.TaskForLocks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Програмне завдання. Із використанням засобів ресурсів пакету
 * java.util.concurrent.locks створити синхронну багатопотокову систему для визначення
 * загальної суми всіх цифр в трьох текстових файлах. У випадку блокування потоку,
 * він повинен виводити своє ім’я з маркою “LOCKED”. НЕ ВИКОРИСТОВУВАТИ
 * проміжне створення колекцій для прочитаного тексту.
 */

public class Dispatcher {

    public static void main(String[] args) throws InterruptedException {
        String[] filenames = {
                "src/main/java/com/olehbilykh/camp/TaskForLocks/inputFiles/first.txt",
                "src/main/java/com/olehbilykh/camp/TaskForLocks/inputFiles/second.txt",
                "src/main/java/com/olehbilykh/camp/TaskForLocks/inputFiles/third.txt"
        };
        Thread[] threads = new Thread[filenames.length];
        int i = 0;
        for (String filename : filenames) {
            Thread t = new Thread(new CustomThread(filename));
            threads[i++] = t;
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Total sum: " + Result.sum);
    }

    static class Result {
        private static final ReentrantLock sumLock = new ReentrantLock();
        private static int sum = 0;
    }

    private static class CustomThread implements Runnable {
        private final String filename;

        public CustomThread(String filename) {
            this.filename = filename;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sumDigits(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sumDigits(String text) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isDigit(c)) {
                    Result.sumLock.lock();
                    Result.sum += Character.getNumericValue(c);
                    Result.sumLock.unlock();
                }
            }
        }
    }
}