package com.olehbilykh.camp.TaskForLines;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * На площині задано N точок. Необхідно у файл LINES записати
 * два HashMap:
 * - із ключами у вигляді об’єктів класу Point, що визначаються цілими координатами
 * точок, та значеннями у вигляді кількості прямих, що проходять через цю точку та,
 * щонайменше, ще через одну точку,
 * https://www.geeksforgeeks.org/print-nodes-having-maximum-and-minimum-degrees/
 * - із ключами у вигляді об’єктів класу Line, що визначаються параметрами К та В
 * прямої y = K*x + B, та значеннями у вигляді кількості точок, що належать цій прямій.
 * https://stackoverflow.com/questions/18059793/to-determine-if-a-point-lies-on-a-line
 */

public class Dispatcher {
    public static void main(String[] args) {
        File lines = new File("src/com/sigma/camp/TaskForLines/lines.ser");

        Map<Point, Integer> firstMap = new HashMap<>();
        Map<Line, Integer> secondMap = new HashMap<>();
    }
}

class Line {
    private Point a;
    private Point b;

    public Line() {
        this.a = new Point();
        this.b = new Point();
    }

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Line{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}

class Point implements Serializable {
    private int x;
    private int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
