package com.olehbilykh.camp.TaskForSerialization;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dispatcher {
    public static void main(String[] args) {
        List<Plane> planes = new ArrayList<>(List.of(
                new Plane(new Engine("rocketFuel", 123000), 2000, 5000, "Jet", 800, new Chassis(new Wheel(1, 15), 6)),
                new Plane(new Engine("diesel", 1200), 2012, 1000, "Cargo", 1500, new Chassis(new Wheel(2, 100), 12)),
                new Plane(new Engine("benzine", 23000), 1991, 1500, "Passenger", 1000, new Chassis(new Wheel(1.7, 70), 8))
        ));
        planes.sort(Comparator.comparing(Vehicle::getYearOfProduction));
        System.out.println("Before " + planes);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/com/sigma/camp/TaskForSerialization/planes.ser"))) {
            out.writeObject(planes);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<Plane> planesDeserealized = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/com/sigma/camp/TaskForSerialization/planes.ser"))) {
            planesDeserealized = (List<Plane>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("After " + planesDeserealized);


        List<Ship> ships = new ArrayList<>(List.of(
                new Ship(new Engine("uranium", 121312), 2022, 100, 500, 200, new Bumboat(3, "wood")),
                new Ship(new Engine("diesel", 121123), 2012, 500, 50, 100, new Bumboat(13, "plastic")),
                new Ship(new Engine("kerosene", 13123), 1999, 60, 300, 400, new Bumboat(33, "metal"))
        ));
        ships.sort(Comparator.comparing(Vehicle::getYearOfProduction));
        System.out.println("Before " + ships);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/com/sigma/camp/TaskForSerialization/ships.ser"))) {
            out.writeObject(ships);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<Ship> shipsDeserealized = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/com/sigma/camp/TaskForSerialization/ships.ser"))) {
            shipsDeserealized = (List<Ship>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("After " + shipsDeserealized);
    }
}

class Vehicle {
    private Engine engine;
    private int yearOfProduction;
    private double speed;

    public Vehicle() {
    }

    public Vehicle(Engine engine, int yearOfProduction, double speed) {
        this.engine = engine;
        this.yearOfProduction = yearOfProduction;
        this.speed = speed;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "engine=" + engine +
                ", yearOfProduction=" + yearOfProduction +
                ", speed=" + speed +
                '}';
    }
}

class Plane extends Vehicle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String model;
    private double maxFlightLength;
    private transient Chassis chassis;

    public Plane() {
    }

    public Plane(Engine engine, int yearOfProduction, double speed, String model, double maxFlightLength, Chassis chassis) {
        super(engine, yearOfProduction, speed);
        this.model = model;
        this.maxFlightLength = maxFlightLength;
        this.chassis = chassis;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getMaxFlightLength() {
        return maxFlightLength;
    }

    public void setMaxFlightLength(double maxFlightLength) {
        this.maxFlightLength = maxFlightLength;
    }

    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) {

        try {
            out.writeDouble(getSpeed());
            out.writeInt(getYearOfProduction());
            out.writeObject(getEngine().getType());
            out.writeDouble(getEngine().getPower());

            out.writeDouble(chassis.getWheel().getLoad());
            out.writeDouble(chassis.getWheel().getDiameter());
            out.writeInt(chassis.getNumberOfWheels());

            out.defaultWriteObject();
        } catch (IOException e) {
            System.out.println("Exception when trying to serialize: " + e.getMessage());
        }

    }

    @Serial
    private void readObject(ObjectInputStream in) {
        try {
            this.setSpeed(in.readDouble());
            this.setYearOfProduction(in.readInt());
            this.setEngine(new Engine((String) in.readObject(), in.readDouble()));
            this.chassis = new Chassis(new Wheel(in.readDouble(), in.readDouble()), in.readInt());
            in.defaultReadObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception when trying to deserialize: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "\nPlane{" +
                "speed=" + getSpeed() + ", " + getEngine() + ", " +
                "productionYear=" + getYearOfProduction() + ", " +
                "model='" + model + '\'' +
                ", maxFlightLength=" + maxFlightLength + ", " +
                chassis + '}';
    }
}

class Ship extends Vehicle implements Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;
    private double waterTonnage;
    private double length;
    private Bumboat bumboat;

    public Ship() {
    }

    public Ship(Engine engine, int yearOfProduction, double speed, double waterTonnage, double length, Bumboat bumboat) {
        super(engine, yearOfProduction, speed);
        this.waterTonnage = waterTonnage;
        this.length = length;
        this.bumboat = bumboat;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeDouble(waterTonnage);
        out.writeDouble(length);
        out.writeInt(bumboat.getNumberOfPassengers());
        out.writeUTF(bumboat.getMaterial());

        out.writeDouble(getSpeed());
        out.writeInt(getYearOfProduction());
        out.writeUTF(getEngine().getType());
        out.writeDouble(getEngine().getPower());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        this.waterTonnage = in.readDouble();
        this.length = in.readDouble();
        this.bumboat = new Bumboat(in.readInt(), in.readUTF());

        this.setSpeed(in.readDouble());
        this.setYearOfProduction(in.readInt());
        this.setEngine(new Engine(in.readUTF(), in.readDouble()));
    }

    public double getWaterTonnage() {
        return waterTonnage;
    }

    public void setWaterTonnage(double waterTonnage) {
        this.waterTonnage = waterTonnage;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Bumboat getBumboat() {
        return bumboat;
    }

    public void setBumboat(Bumboat bumboat) {
        this.bumboat = bumboat;
    }

    @Override
    public String toString() {
        return "\nShip{" +
                "speed=" + getSpeed() + ", " + getEngine() + ", " +
                "productionYear=" + getYearOfProduction() + ", " +
                "waterTonnage=" + waterTonnage +
                ", length=" + length +
                ", " + bumboat + '}';
    }
}

class Engine {
    private String type;
    private double power;

    public Engine(String type, double power) {
        this.type = type;
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "type='" + type + '\'' +
                ", power=" + power +
                '}';
    }
}

class Chassis {
    private Wheel wheel;
    private int numberOfWheels;

    public Chassis() {
    }

    public Chassis(Wheel wheel, int numberOfWheels) {
        this.wheel = wheel;
        this.numberOfWheels = numberOfWheels;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    @Override
    public String toString() {
        return "Chassis{" +
                "wheel=" + wheel +
                ", numberOfWheels=" + numberOfWheels +
                '}';
    }
}

class Bumboat {
    private int numberOfPassengers;
    private String material;

    public Bumboat(int numberOfPassengers, String material) {
        this.numberOfPassengers = numberOfPassengers;
        this.material = material;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Bumboat{" +
                "numberOfPassengers=" + numberOfPassengers +
                ", material='" + material + '\'' +
                '}';
    }
}

class Wheel {
    private double diameter;
    private double load;

    public Wheel() {
    }

    public Wheel(double diameter, double load) {
        this.diameter = diameter;
        this.load = load;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "diameter=" + diameter +
                ", load=" + load +
                '}';
    }
}
