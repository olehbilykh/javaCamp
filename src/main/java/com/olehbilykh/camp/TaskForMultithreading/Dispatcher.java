package com.olehbilykh.camp.TaskForMultithreading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Створити багатопотокову систему для визначення кількості
 * розділових знаків в трьох текстових файлах по кожному файлу окремо. Для кожного
 * файлу за власним вибором обрати свій спосіб створення потоку (за допомогою
 * інтерфейсів Runnable, Callable, Executor, ExecutorService). НЕ ВИКОРИСТОВУВАТИ
 * для прочитаного тексту створення проміжних колекцій або цілий String.
 * Порівняти часові витрати при вирішенні завдання одно- та багатопотоковим способами.
 * СТРОГО ПІСЛЯ завершення читання даних з файлів для збереження результатів
 * створити TreeSet, посортований за кількістю розділових знаків у файлах від більшого
 * значення до меншого, (використати обгортку або підклас класу File).
 */
public class Dispatcher {
    public static void main(String[] args) throws Exception {
        Runnable r = () -> {
            try (Scanner scanner = new Scanner(new File("src/com/sigma/camp/TaskForMultithreading/inputFiles/first.txt"))) {
                String tmp = "";
                while (scanner.hasNext()) {
                    if (!scanner.hasNext("\\p{Punct}&&[^@',&]]")) {
                        tmp = scanner.next();
                    } else {
                        scanner.next();
                    }
                }
                System.out.println(tmp.length());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };

        new Thread(r).start();


    }
}

class CustomFile implements Callable<Integer> {


    @Override
    public Integer call() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(""));
        String tmp = "";
        while (scanner.hasNext()) {
            if (!scanner.hasNext("\\p{Punct}&&[^@',&]]")) {
                tmp = scanner.next();
            } else {
                scanner.next();
            }
        }
        return tmp.length();
    }

}