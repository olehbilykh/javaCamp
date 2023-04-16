package com.olehbilykh.camp.TaskForFinal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Dispatcher {
    public static void main(String[] args) {
        Student s1 = new Student(
                1,
                "John",
                new HashMap<>(Map.of("TaskForFinal", 99, "TaskForBirthdays", 100))
        );

        Student s2 = new Student(
                2,
                "Max",
                new HashMap<>(Map.of("TaskForFinal", 0, "TaskForBirthdays", 54))
        );

        Student s3 = new Student(
                3,
                "Karl",
                new HashMap<>(Map.of("TaskForFinal", 1, "TaskForBirthdays", 2))
        );

        final Group group = new Group(
                "javaCamp",
                new LinkedList<>(
                        List.of(s1, s2, new Student(4, "Frank", new HashMap<>(Map.of("TaskForFinal", 333, "TaskForBirthdays", 222))))
                )
        );

        group.addStudent(s3);
        System.out.println(group);
        group.removeStudent(3);
        System.out.println(group);

        group.getStudents().get(2).changeMarkByAssignment("TaskForFinal", 100);
        group.getStudents().get(1).addMark("TaskBlabla", 123);

        s2.changeMarkByAssignment("TaskForBirthdays", 45);


//        group.getStudents().get(1).getMarks().put("bla", 24);

        System.out.println("-> " + group);

        group.getStudents().get(2).graduate();
        System.out.println(group);

        group.graduate();
        System.out.println(group);


        try {
            group.addStudent(s3);
            s2.addMark("random", 34);
        } catch (Exception e) {
            System.out.println("You cannot apply changes after graduation");
        }

        group.getStudents().get(1).setName("Johny");
        s1.setName("Montana");

        System.out.println(group.getStudents().get(1));

        System.out.println(group);

    }
}

final class Group {
    private String title;
    private Map<Integer, Student> students;
    private boolean isGraduated;

    public Group() {
    }

    public Group(String title, List<Student> students) {
        this.title = title;
        this.students = students
                .stream()
                .collect(Collectors.toMap(Student::getId, Function.identity()));
        this.isGraduated = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public boolean isGraduated() {
        return isGraduated;
    }

    public void graduate() {
        isGraduated = true;
        students.values().forEach(Student::graduate);
        students = Collections.unmodifiableMap(students);
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public void removeStudent(int id) {
        students.remove(id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "students=" + students +
                '}';
    }
}

final class Student {
    private final int id;
    private String name;
    private Map<String, Integer> marks;
    private boolean isGraduated;

    public Student(int id, String name, Map<String, Integer> marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.isGraduated = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!isGraduated)
            this.name = name;
    }

    public boolean isGraduated() {
        return isGraduated;
    }

    public Map<String, Integer> getMarks() {
        return isGraduated ? marks : Collections.unmodifiableMap(marks);
    }

    public void addMark(String assignment, int mark) {
        marks.put(assignment, mark);
    }

    public void changeMarkByAssignment(String assignment, int mark) {
        marks.replace(assignment, mark);
    }

    public void graduate() {
        isGraduated = true;
        marks = Collections.unmodifiableMap(marks);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                ", isGraduated=" + isGraduated +
                '}';
    }
}

class GroupTest {
    @Test
    void addStudent_shouldAddNewStudent_whenGroupIsNotGraduated() {
        Group group = new Group("foo", new LinkedList<>());
        Student student = new Student(1, "bar", new HashMap<>());
        group.addStudent(student);

        Assertions.assertEquals(Map.of(1, student), group.getStudents());
    }

    @Test
    void addStudent_shouldThrowException_whenGroupIsGraduated() {
        Group group = new Group("foo", new LinkedList<>());
        Student student = new Student(1, "bar", new HashMap<>());
        group.graduate();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> group.addStudent(student));
    }
}