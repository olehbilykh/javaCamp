package com.olehbilykh.camp.TaskForMultithreading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.*;

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
    public static void main(String[] args) {
        System.out.println(Controller.createSetOfMyFiles(
                new FileWrapper("src/main/java/com/olehbilykh/camp/TaskForMultithreading/inputFiles/first.txt"),
                new FileWrapper("src/main/java/com/olehbilykh/camp/TaskForMultithreading/inputFiles/second.txt"),
                new FileWrapper("src/main/java/com/olehbilykh/camp/TaskForMultithreading/inputFiles/third.txt"))
        );
    }
}

class Controller {
    public static Set<FileWrapper> createSetOfMyFiles(FileWrapper... files) {
        Set<FileWrapper> result = new TreeSet<>();
        List<FileHandler> fileHandlers = new ArrayList<>();
        for (FileWrapper file : files) {
            fileHandlers.add(new FileHandler(file));
        }

        try (ExecutorService ex = Executors.newFixedThreadPool(files.length)) {
            List<Future<FileWrapper>> futures = ex.invokeAll(fileHandlers);
            for (Future<FileWrapper> future : futures) {
                result.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

class FileWrapper implements Comparable<FileWrapper> {
    private int count;
    private File file;

    public FileWrapper(File file) {
        this.file = file;
    }

    public FileWrapper(String path) {
        this.file = new File(path);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public String toString() {
        return "FileWrapper{" +
                "count=" + count +
                ", file=" + file +
                '}';
    }

    @Override
    public int compareTo(FileWrapper o) {
        return o.count - count;
    }

}

class FileHandler implements Callable<FileWrapper> {
    private final FileWrapper file;

    public FileHandler(FileWrapper file) {
        this.file = file;
    }

    @Override
    public FileWrapper call() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getFile()))) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                char c = (char) symbol;
                if (c == '.' || c == ',' || c == ';' || c == ':' || c == '!' || c == '?') {
                    file.incrementCount();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
