package com.olehbilykh.camp.TaskForParsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Програмне завдання. За допомогою класів Pattern, Matcher / Scanner (за вибором
 * слухача) в заданому текстовому файлі видалити всі слова заданої довжини, що
 * починаються на приголосну букву. Опрацьовані фрагменти тексту заносити до нового
 * текстового файлу безпосередньо після опрацювання без утворення буферних
 * колекцій або без перетворення всього файлу у текст.
 */
public class Dispatcher {
    public static void main(String[] args) {
        File file = new File("src/com/sigma/camp/TaskForParsing/input.txt");
        int length = 5;
        try (Scanner scanner = new Scanner(file); PrintWriter printWriter = new PrintWriter("src/com/sigma/camp/TaskForParsing/output.txt");) {
            String tmp = "";
            while (scanner.hasNext()) {
                if (!scanner.hasNext("(?i)[^aeiouy]\\w{" + (length - 1) + "}")) {
                    tmp = scanner.next();
                    System.out.print(tmp + " ");
                    printWriter.print(tmp + " ");
                } else {
                    scanner.next();
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        }
    }
}