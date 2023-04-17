package com.olehbilykh.camp.TaskForThreadSafeCollections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Із використанням потокобезпечних колекцій пакету
 * java.util.concurrent створити синхронну багатопотокову систему для формування з
 * трьох текстових файлів колекції з кількістю повторень кожного слова ДОВЖИНОЮ
 * БІЛЬШЕ ОДНІЄЇ БУКВИ, В ЯКИХ ЗБІГАЄТЬСЯ ПЕРША ТА ОСТАННЯ
 * БУКВИ. НЕ ВИКОРИСТОВУВАТИ проміжне створення колекцій для прочитаного
 * тексту.
 */
public class Dispatcher {
    static List<String> files = new ArrayList<>(List.of(
            "src/com/sigma/camp/TaskForThreadSafeCollections/inputFiles/first.txt",
            "src/com/sigma/camp/TaskForThreadSafeCollections/inputFiles/second.txt",
            "src/com/sigma/camp/TaskForThreadSafeCollections/inputFiles/third.txt"
    ));

    public static void main(String[] args) {
        Map<String, Integer> map = Controller.countWordsWithSameBoundariesInFiles(files);
        System.out.println(map);
    }
}

class Controller {
    public static Map<String, Integer> countWordsWithSameBoundariesInFiles(List<String> files) {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
        ExecutorService es = null;
        try {
            es = Executors.newFixedThreadPool(files.size());
            for (String file : files) {
                es.execute(new CustomThread(map, file));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            es.shutdown();
        }

        return map;
    }
}

class CustomThread implements Runnable {

    private String file;
    private Map<String, Integer> map;

    public CustomThread(Map<String, Integer> map, String file) {
        this.file = file;
        this.map = map;
    }

    @Override
    public void run() {
        try (BufferedReader bf = new BufferedReader(new FileReader(this.file))) {
            Pattern p = Pattern.compile("\\b([a-zA-Z])\\w*\\1\\b");
            Matcher m;
            String line;
            while ((line = bf.readLine()) != null) {
                m = p.matcher(line);
                while (m.find()) {
                    map.compute(m.group(), (word, count) -> count == null ? 1 : ++count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
