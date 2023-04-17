package com.olehbilykh.camp.TaskForJDBC;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Прочитати та занести дані у відповідні колекції дані з наявної
 * бази у форматі:
 * N Bottle Volume Material
 * 1 Wine 0.75 Glass
 * 2 Juice 0.25 Metal
 * ...
 * Завдання для формування запитів:
 * - зчитувати пляшки місткістю: не більше 0.5 л, в межах від 0.51л до 0.99 л, не
 * меншою за 1.0 л,
 * - зчитувати пляшки для: вина, соків, води,
 * - зчитувати пляшки, зроблені з: металу, шкла, пластику.
 */


public class Dispatcher {
    public static void main(String[] args) {
        System.out.println(new Barmen().showMenu());
    }
}

class Barmen {
    private final String password = "postgres";
    private final String user = "postgres";
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String allQuery = "SELECT id, type, volume, material FROM beverages";
    private final String smallDrinksQuery = "SELECT id, type, volume, material FROM beverages WHERE volume <= 0.5";
    private final String middleDrinksQuery = "SELECT id, type, volume, material FROM beverages WHERE volume BETWEEN 0.5 and 1";
    private final String largeDrinksQuery = "SELECT id, type, volume, material FROM beverages WHERE volume >= 1";
    private final String winesQuery = "SELECT id, type, volume, material FROM beverages WHERE type = 'wine'";
    private final String watersQuery = "SELECT id, type, volume, material FROM beverages WHERE type = 'water'";
    private final String juicesQuery = "SELECT id, type, volume, material FROM beverages WHERE type = 'juice'";

    private final String metalCansQuery = "SELECT id, type, volume, material FROM beverages WHERE type = 'wine'";
    private final String glassBottlesQuery = "SELECT id, type, volume, material FROM beverages WHERE type = 'wine'";
    private final String plasticBottlesQuery = "SELECT id, type, volume, material FROM beverages WHERE type = 'wine'";
    private final Map<Integer, Beverage> beverages;

    public Barmen() {
        beverages = new HashMap<>();
    }

    private void checkDb(String query) {
        Beverage beverage;
        try (Connection connection = DriverManager
                .getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                beverage = new Beverage();
                beverage.setId(resultSet.getInt("id"));
                beverage.setType(resultSet.getString("type"));
                beverage.setVolume(resultSet.getDouble("volume"));
                beverage.setMaterial(resultSet.getString("material"));
                beverages.put(beverage.getId(), beverage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Beverage> showMenu() {
        checkDb(allQuery);
        return beverages.values();
    }

    public Collection<Beverage> getSmallDrinks() {
        checkDb(smallDrinksQuery);
        return beverages.values();
    }
}

class Beverage {
    private int id;
    private String type;
    private double volume;
    private String material;

    public Beverage(int id, String type, double volume, String material) {
        this.id = id;
        this.type = type;
        this.volume = volume;
        this.material = material;
    }

    public Beverage() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beverage beverage = (Beverage) o;

        if (id != beverage.id) return false;
        if (Double.compare(beverage.volume, volume) != 0) return false;
        if (!type.equals(beverage.type)) return false;
        return material.equals(beverage.material);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + type.hashCode();
        temp = Double.doubleToLongBits(volume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + material.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", volume=" + volume +
                ", material='" + material + '\'' +
                '}';
    }
}