package com.olehbilykh.camp.TaskForFiles;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Dispatcher {
    public static void main(String[] args) {
        final File filesInFolder = new File("src/com/sigma/camp/TaskForFiles/InputFiles");
        List<File> files = listFilesForFolder(filesInFolder);
        String[] strings;

        List<Drink> lessThan05 = new LinkedList<>();
        List<Drink> moreThan05lessThan1 = new LinkedList<>();
        List<Drink> moreThan1 = new LinkedList<>();


        for (int i = 0; i < files.size(); i++) {
            try (BufferedReader bf = new BufferedReader(new FileReader(listFilesForFolder(filesInFolder).get(i)))) {
                String tmp;
                while ((tmp = bf.readLine()) != null) {
                    strings = tmp.split(" ");
                    Drink drink = new Drink(strings[1], Double.parseDouble(strings[2]), strings[3]);

                    if (drink.getVolume() > 0.5 && drink.getVolume() < 1) {
                        moreThan05lessThan1.add(drink);
                    } else {
                        if (drink.getVolume() < 0.51)
                            lessThan05.add(drink);
                        else moreThan1.add(drink);
                    }

                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        Dispatcher.sort(lessThan05, args[0]);
        Dispatcher.writeToFile(lessThan05, "src/com/sigma/camp/TaskForFiles/OutputFiles/lessThan05");
        Dispatcher.sort(moreThan05lessThan1, args[1]);
        Dispatcher.writeToFile(moreThan05lessThan1, "src/com/sigma/camp/TaskForFiles/OutputFiles/moreThan05lessThan1");
        Dispatcher.sort(moreThan1, args[2]);
        Dispatcher.writeToFile(moreThan1, "src/com/sigma/camp/TaskForFiles/OutputFiles/moreThan1");

        System.out.println("< 0.5 " + lessThan05);
        System.out.println("> 0.5 && 1 < " + moreThan05lessThan1);
        System.out.println("> 1 " + moreThan1);

    }

    public static List<File> listFilesForFolder(final File folder) {
        List<File> fileEntries = new LinkedList<>();
        try {
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    fileEntries.add(fileEntry);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return fileEntries;
    }

    public static void writeToFile(List<Drink> drinks, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Drink drink : drinks) {
                pw.println(drink);
            }
            pw.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sort(List<Drink> drinks, String input) {
        switch (input) {
            case "1" -> drinks.sort((d1, d2) -> {
                int result = d1.getType().compareTo(d2.getType());
                if (result == 0) {
                    result = Double.compare(d1.getVolume(), d2.getVolume());
                    if (result == 0) {
                        result = d1.getBottleMaterial().compareTo(d2.getBottleMaterial());
                    }
                }
                return result;
            });

            case "2" -> drinks.sort((d1, d2) -> {
                int result = d1.getBottleMaterial().compareTo(d2.getBottleMaterial());
                if (result == 0) {
                    result = Double.compare(d1.getVolume(), d2.getVolume());
                    if (result == 0) {
                        result = d1.getType().compareTo(d2.getType());
                    }
                }
                return result;
            });

            default -> drinks.sort(Drink::compareTo);
        }
    }

}

class Drink implements Comparable<Drink> {
    private String type;
    private double volume;
    private String bottleMaterial;

    public Drink(String type, double volume, String bottleMaterial) {
        this.type = type;
        this.volume = volume;
        this.bottleMaterial = bottleMaterial;
    }

    @Override
    public int compareTo(Drink o) {
        int result = Double.compare(this.getVolume(), o.getVolume());
        if (result == 0) {
            result = this.bottleMaterial.compareTo(o.bottleMaterial);
            if (result == 0) {
                result = this.type.compareTo(o.type);
            }
        }

        return result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getBottleMaterial() {
        return bottleMaterial;
    }

    public void setBottleMaterial(String bottleMaterial) {
        this.bottleMaterial = bottleMaterial;
    }

    @Override
    public String toString() {
        return type + " " + volume + " " + bottleMaterial;
    }
}