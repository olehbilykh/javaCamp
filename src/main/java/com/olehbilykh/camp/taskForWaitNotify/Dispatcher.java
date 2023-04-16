package com.olehbilykh.camp.taskForWaitNotify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    static List<String> files = new ArrayList<>(List.of(
            "src/com/sigma/camp/TaskForThreadSafeCollections/inputFiles/first.txt",
            "src/com/sigma/camp/TaskForThreadSafeCollections/inputFiles/second.txt",
            "src/com/sigma/camp/TaskForThreadSafeCollections/inputFiles/third.txt"
    ));

    public static void main(String[] args) {

        System.out.println(new FileHandler().countSpaces(files.get(0)));
    }
}

class Controller {

}

class FileHandler {
    public int countSpaces(String file) {
        int whitespaceCounter = 0;
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            int charCode;
            while ((charCode = bf.read()) != -1) {
                char ch = (char) charCode;
                if (Character.isWhitespace(ch)) {
                    whitespaceCounter++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return whitespaceCounter;
    }

    public void capitalizeFirstLetters(String file) {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
