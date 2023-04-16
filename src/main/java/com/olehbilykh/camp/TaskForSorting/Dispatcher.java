package com.olehbilykh.camp.TaskForSorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Dispatcher {
    private static final Map<String, Comparator<Person>> peopleComparator = new HashMap<>();

    static {
        peopleComparator.put("1", (o1, o2) -> {
            int result = o1.getRegion().compareTo(o2.getRegion());
            if (result == 0) {
                result = o1.getName().compareTo(o2.getName());
                if (result == 0) {
                    result = Boolean.compare(o1.isMale(), o2.isMale());
                    if (result == 0) {
                        return Integer.compare(o1.getBirthYear(), o2.getBirthYear());
                    }
                }
            }
            return result;
        });
        peopleComparator.put("2", (o1, o2) -> {
            int result = Integer.compare(o1.getBirthYear(), o2.getBirthYear());
            if (result == 0) {
                result = Boolean.compare(o1.isMale(), o2.isMale());
                if (result == 0) {
                    result = o1.getName().compareTo(o2.getName());
                    if (result == 0) {
                        result = o1.getRegion().compareTo(o2.getRegion());
                    }
                }
            }
            return result;
        });
        peopleComparator.put("3", (o1, o2) -> {
            int result = Boolean.compare(o1.isMale(), o2.isMale());
            if (result == 0) {
                result = o1.getRegion().compareTo(o2.getRegion());
                if (result == 0) {
                    result = Integer.compare(o1.getBirthYear(), o2.getBirthYear());
                }
            }
            return result;
        });
    }

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("AAA", "RRR", 2006, true),
                new Person("AAA", "RRR", 2004, true),
                new Person("AAA", "RRR", 2005, true),
                new Person("AAA", "RRR", 2001, true),
                new Person("AAA", "RRR", 2003, true),
                new Person("AAA", "RRR", 2002, true)
        );

        String input = "1";
        Dispatcher.sort(people, input);
        System.out.println(people);
    }

    public static void sort(List<Person> people, String input) {
        people.sort(peopleComparator.getOrDefault(input, Person::compareTo));
    }
}

class Person implements Comparable {
    private String name;
    private String region;
    private int birthYear;
    private boolean isMale;

    public Person(String name, String region, int birthYear, boolean isMale) {
        this.name = name;
        this.region = region;
        this.birthYear = birthYear;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public int compareTo(Object o) {
        int result = this.name.compareTo(((Person) o).getName());
        if (result == 0) {
            result = this.region.compareTo(((Person) o).getRegion());
            if (result == 0) {
                result = Integer.compare(this.birthYear, (((Person) o).getBirthYear()));
                if (result == 0) {
                    result = Boolean.compare(this.isMale, (((Person) o).isMale));
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return birthYear == person.birthYear && isMale == person.isMale && name.equals(person.name) && region.equals(person.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, region, birthYear, isMale);
    }

    @Override
    public String toString() {
        return "\nPerson{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", birthYear=" + birthYear +
                ", isMale=" + isMale +
                '}';
    }
}

class SortingTest {
    @Test
    void testFirstScenario() {
        List<Person> people = Arrays.asList(

                new Person("AAA", "RRR", 2000, true),
                new Person("AAA", "RRR", 1999, true),
                new Person("AAA", "RRR", 2002, true)
        );
        Dispatcher.sort(people, "1");
        List<Person> sorted = Arrays.asList(

                new Person("AAA", "RRR", 1999, true),
                new Person("AAA", "RRR", 2000, true),
                new Person("AAA", "RRR", 2002, true)
        );
        Assertions.assertIterableEquals(sorted, people);
    }

    @Test
    void testSecondScenario() {
        List<Person> people = Arrays.asList(

                new Person("AAA", "CCC", 2000, true),
                new Person("AAA", "AAA", 2000, true),
                new Person("AAA", "BBB", 2000, true)
        );
        Dispatcher.sort(people, "2");
        List<Person> sorted = Arrays.asList(
                new Person("AAA", "AAA", 2000, true),
                new Person("AAA", "BBB", 2000, true),
                new Person("AAA", "CCC", 2000, true)
        );
        Assertions.assertIterableEquals(sorted, people);
    }

    @Test
    void testThirdScenario() {
        List<Person> people = Arrays.asList(
                new Person("AAA", "AAA", 1000, true),
                new Person("AAA", "AAA", 2000, true),
                new Person("AAA", "AAA", 3000, true)
        );
        Dispatcher.sort(people, "3");
        List<Person> sorted = Arrays.asList(
                new Person("AAA", "AAA", 1000, true),
                new Person("AAA", "AAA", 2000, true),
                new Person("AAA", "AAA", 3000, true)
        );
        Assertions.assertIterableEquals(sorted, people);
    }

    @Test
    void testDefaultScenario() {
        List<Person> people = Arrays.asList(
                new Person("AAA", "RRR", 1000, true),
                new Person("AAA", "RRR", 1000, false),
                new Person("AAA", "RRR", 1000, false)
        );

        Dispatcher.sort(people, "bla");
        List<Person> sorted = Arrays.asList(
                new Person("AAA", "RRR", 1000, false),
                new Person("AAA", "RRR", 1000, false),
                new Person("AAA", "RRR", 1000, true)
        );
        Assertions.assertIterableEquals(sorted, people);
    }
}