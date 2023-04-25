package com.olehbilykh.camp.TaskForStreamsBasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * На основі методів інтерфейсу Stream виконати наступні дії:
 * - в тексті реалізувати початок кожного слова з прописної (великої) букви;
 * - вивести всi речення тексту за зростанням кількості слів, припустимо, що всі речення
 * завершуються тільки крапкою;
 * - колекцію цілих чисел поділити на колекції з додатніх та від’ємних елементів.
 */
public class Dispatcher {
    static String s = """
            Continual delighted as elsewhere am convinced unfeeling. Introduced stimulated attachment no by projection.
            To loud lady whom my mile sold four. Need miss all four case fine age tell. He families my pleasant speaking it bringing it thoughts.
            View busy dine oh in knew if even. Boy these along far own other equal old fanny charm. Difficulty invitation put introduced see middletons nor preference.
            Whether article spirits new her covered hastily sitting her. Money witty books nor son add. John. John Doe. John Doe Junior.
            """;

    public static void main(String[] args) {
        List<String> list = Arrays.stream(s.split("[^\\w']+")).map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1)).toList();
        List<String> sentences = new ArrayList<>(List.of(s.split("\\.")));
//        sentences.stream().sorted(Comparator.comparingInt(String::length)).forEach(System.out::println);
        List<Integer> ints = new ArrayList<>(List.of(-5, 3, 2, Integer.MAX_VALUE, 0, -25, Integer.MIN_VALUE, 324243, -54345));
        List<Integer> negativeInts = ints.stream().filter(i -> i < 0).toList();
        List<Integer> positiveInts = ints.stream().filter(i -> i >= 0).toList();
//        negativeInts.forEach(System.out::println);
//        positiveInts.forEach(System.out::println);

        int[] mergeSorted = mergeSorted(new int[]{1, 4, 7}, new int[]{2, 5, 9});
        Arrays.stream(mergeSorted).forEach(System.out::println);
    }

    // i1 1 4 7
    // i2 2 5 9
    // out 1 2 4 5 7 9
    public static int[] mergeSorted(int[] input1, int[] input2) {
        return IntStream.concat(IntStream.of(input1), IntStream.of(input2)).sorted().toArray();
    }
}
