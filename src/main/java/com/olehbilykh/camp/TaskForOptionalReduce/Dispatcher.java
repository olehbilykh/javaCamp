package com.olehbilykh.camp.TaskForOptionalReduce;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * На основі методів потокового зведення інтерфейсу Stream виконати дії:
 * - утворити третій контейнер ArrayList як конкатенацію двох масивів різної довжини;
 * - визначити середнє арифметичне елементів масиву цілих чисел та кількість
 * елементів, що є більшими за середнє арифметичне;
 * - з колекції цілих чисел видалити дублікати максимума та мінімума.
 * ------------------------------------------------------------------------------------
 * На основі методів класу Collectors виконати дії:
 * - в колекції цілих чисел поміняти місцями максимум та мінімум;
 * - визначити середнє арифметичне елементів колекції цілих чисел та сформувати
 * вихідну колекцію з елементів, що є більшими за середнє арифметичне;
 * - в кожному реченні тексту без використання попереднього розбиття на речення
 * визначити різницю між кількістю приголосних та голосних букв і сформувати
 * відповідний Map (key: номер речення, value: визначена різниця між кількістю
 * приголосних та голосних букв).
 */
public class Dispatcher {
    static String s = """
            Continual delighted as elsewhere am convinced unfeeling. Introduced stimulated attachment no by projection.
            To loud lady whom my mile sold four. Need miss all four case fine age tell. He families my pleasant speaking it bringing it thoughts.
            View busy dine oh in knew if even. Boy these along far own other equal old fanny charm. Difficulty invitation put introduced see middletons nor preference.
            Whether article spirits new her covered hastily sitting her. Money witty books nor son add. John. John Doe. John Doe Junior.
            """;

    public static void main(String[] args) {
        int[] ints1 = {-5, 3, 2, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, -25, Integer.MIN_VALUE, 324243, -54345, Integer.MIN_VALUE};
        int[] ints2 = {-5, 2, 0, -25, Integer.MIN_VALUE, 1223, -54345};
        List<Integer> integerCollection =
                IntStream.concat(Arrays.stream(ints1), Arrays.stream(ints2)).boxed().collect(Collectors.toCollection(ArrayList::new));
        OptionalDouble averageOfInts1 = IntStream.of(ints1).average();
        long numOfIntsGreaterThenAvg = IntStream.of(ints1).filter(i -> i > averageOfInts1.getAsDouble()).count();
        Optional<Integer> min = integerCollection.stream().min(Integer::compareTo);
        Optional<Integer> max = integerCollection.stream().max(Integer::compareTo);
        IntStream concat = IntStream.concat(IntStream.concat(integerCollection.stream()
                .mapToInt(Integer::intValue)
                .filter(i -> !Objects.equals(i, min.get()) && !Objects.equals(i, max.get())), IntStream.of(min.get())), IntStream.of(max.get()));
        concat.forEach(System.out::println);
        List<Integer> list = List.of(1, 2, 3, 4, 5, -1, 0, -6, -123314);

        int min1 = list.stream().min(Integer::compare).orElseThrow();
        int max1 = list.stream().max(Integer::compare).orElseThrow();
        List<Integer> minAndMaxSwapped = list.parallelStream()
                .map(i -> i == min1 ? max1 : i == max1 ? min1 : i)
                .toList();
        List<Integer> list1 = List.of(1, 2, 3, 4, 5, -1, 0, -6, -123314);
        OptionalDouble average = list1.parallelStream().mapToDouble(Integer::doubleValue).average();
        List<Integer> greaterThenAvg = list1.parallelStream().filter(i -> i > average.getAsDouble()).collect(Collectors.toCollection(ArrayList::new));

        AtomicInteger sentenceCount = new AtomicInteger(1);
        Map<Integer, Integer> map = Pattern.compile("[!?.]\\s*")
                .splitAsStream(s)
                .map(String::toLowerCase)
                .collect(Collectors.toMap(
                        sentence -> sentenceCount.getAndIncrement(),
                        sentence -> (int) (sentence.chars().filter(c -> "bcdfghjklmnpqrstvwxyz".indexOf(c) >= 0).count()
                                - sentence.chars().filter(c -> "aeiou".indexOf(c) >= 0).count())));
//        map.forEach(System.out::println);
        System.out.println(map);
    }
}
