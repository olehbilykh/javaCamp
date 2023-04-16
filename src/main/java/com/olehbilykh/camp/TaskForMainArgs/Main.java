package com.olehbilykh.camp.TaskForMainArgs;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        App.run(args);
    }
}

class App {
    public static void run(String[] args) {
        Controller cv = new Controller();
        try {
            System.out.println(cv.returnArgs(TypeArgs.valueOf(args[0]), args));
        } catch (Exception e) {
            System.out.println("Incorrect input parameters");
        }
    }
}

enum TypeArgs {
    WEEK_DAYS(Map.of(1, "Monday", 2, "Tuesday", 3, "Wednesday", 4, "Thursday", 5, "Friday", 6, "Saturday", 7, "Sunday")),
    MARKS(Map.of(0, "Unsatisfactory", 1, "Unsatisfactory", 2, "Unsatisfactory", 3, "Satisfactory", 4, "Good", 5, "Excellent")),
    PLANETS(Map.of(1, "Mercury", 2, "Venus", 3, "Earth", 4, "Mars", 5, "Jupiter", 6, "Saturn", 7, "Uran", 8, "Neptune"));
    private final Map<Integer, String> argsAndValues;

    TypeArgs(Map<Integer, String> argsAndValues) {
        this.argsAndValues = argsAndValues;
    }

    public String getStringByKey(int key) {
        if (argsAndValues.containsKey(key)) {
            return argsAndValues.get(key);
        } else return "No corresponding value for argument " + key;
    }

}

class Controller {
    public List<String> returnArgs(TypeArgs typeArg, String[] args) {
        List<String> listOfValues = new LinkedList<>();
        for (int i = 1; i < args.length; i++) {
            listOfValues.add(typeArg.getStringByKey(Integer.parseInt(args[i])));
        }
        return listOfValues;
    }
}

