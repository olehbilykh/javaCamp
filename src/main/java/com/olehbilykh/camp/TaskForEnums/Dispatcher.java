package com.olehbilykh.camp.TaskForEnums;

import java.util.Comparator;
import java.util.List;

import static com.olehbilykh.camp.TaskForEnums.YearMonth.*;

enum YearMonth {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
}

public class Dispatcher {
    public static void main(String[] args) {
        List<Fest> fests = new java.util.LinkedList<>(List.of(
                new Fest("rock", "Lviv", JUNE),
                new Fest("jazz", "Ternopil", JANUARY),
                new Fest("classic", "Ivano-Frankivsk", MARCH),
                new Fest("techno", "Kyiv", APRIL),
                new Fest("elder", "Donetsk", SEPTEMBER),
                new Fest("drum'n'bass", "Donetsk", SEPTEMBER)

        ));
        Controller.sort(fests, "month");
        System.out.println(fests);
    }
}

class Controller {
    public static void sort(List<Fest> fests, String input) {
        switch (input) {
            case "city" -> fests.sort(Comparator.comparing(Fest::getCity));
            case "month" -> fests.sort(Comparator.comparing(Fest::getMonth));
            default -> fests.sort(Fest::compareTo);
        }
    }
}

class Fest implements Comparable<Fest> {
    private String name;
    private String city;
    private YearMonth month;

    public Fest(String name, String city, YearMonth month) {
        this.name = name;
        this.city = city;
        this.month = month;
    }

    @Override
    public int compareTo(Fest other) {
        return this.name.compareTo(other.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "\nFest{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", month=" + month +
                '}';
    }
}