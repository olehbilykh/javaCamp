package com.olehbilykh.camp.TaskForWaitNotify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Спроектувати двопотокову систему обробки текстових файлів
 * наступним чином: перший потік визначає кількість пробілів у файлі, якщо кількість
 * пробілів є парною - другий потік робить у файлі прописними (великими) перші букви
 * всіх слів, якщо непарною – останні букви. Забезпечити наступність обробки файлів
 * потоками – обробка другим потоком поточного файлу здійснюється під час обробки
 * першим потоком наступних файлів.
 * Порівняти ефективність роботи розробленої системи із класичною багатопотоковою
 * системою, де кожний потік обробляє повністю один файл, а також у випадках обробки
 * файлів приблизно однакового обсягу та з критично різними обсягами.
 */

public class Dispatcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] files = {
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/first.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/second.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/third.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/first.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/second.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/third.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/first.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/second.txt",
                "src/main/java/com/olehbilykh/camp/TaskForWaitNotify/inputFiles/third.txt",
        };
        long startTime = 0;
        // With notify
        startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (String file : files) {
            FileWrapper fileWrapper = new FileWrapper(file);
            executorService.execute(fileWrapper.getFirstAction());
            executorService.execute(fileWrapper.getSecondAction());
        }
        executorService.shutdown();
        System.out.println("With  wait/notify : " + (System.currentTimeMillis() - startTime) + " ms");

        // Without notify
        startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for (String file : files) {
            threads.add(new Thread(() -> {
                long spaceCount;
                try {
                    spaceCount = FileController.countSpaces(file);
                    if (spaceCount % 2 == 0) {
                        FileController.capitalizeFirstLetter(file);
                    } else {
                        FileController.capitalizeLastLatter(file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
        threads.forEach(Thread::run);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Without wait/notify : " + (System.currentTimeMillis() - startTime) + " ms");
    }
}

class FileWrapper {
    private long spaceCount = -1;
    private final String filename;

    public FileWrapper(String filename) {
        this.filename = filename;
    }

    public synchronized void setSpaceCount() {
        try {
            spaceCount = FileController.countSpaces(filename);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        notify();
    }

    public synchronized void capitalize() {
        try {
            while (spaceCount == -1) {
                wait();
            }
        } catch (InterruptedException e1) {
            System.out.println("Interrupted");
        }

        try {
            if (spaceCount % 2 == 0) {
                FileController.capitalizeFirstLetter(filename);
            } else {
                FileController.capitalizeLastLatter(filename);
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public Runnable getFirstAction() {
        return this::setSpaceCount;
    }

    public Runnable getSecondAction() {
        return this::capitalize;
    }

}

class FileController {
    public static long countSpaces(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).mapToLong(line -> line.chars().filter(c -> c == ' ').count()).sum();
    }

    public static void capitalizeFirstLetter(String filename) throws IOException {
        List<String> replaced = Files.lines(Paths.get(filename)).map(line -> capitalizeLettersByPattern("\\b\\w", line))
                .collect(Collectors.toList());
        Files.write(Paths.get(filename), replaced);
    }

    public static void capitalizeLastLatter(String filename) throws IOException {
        List<String> replaced = Files.lines(Paths.get(filename)).map(line -> capitalizeLettersByPattern("\\w\\b", line))
                .collect(Collectors.toList());
        Files.write(Paths.get(filename), replaced);
    }

    private static String capitalizeLettersByPattern(String pattern, String line) {
        Matcher matcher = Pattern.compile(pattern).matcher(line);
        StringBuilder res = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(res, matcher.group().toUpperCase());
        }
        matcher.appendTail(res);
        return res.toString();
    }
}
