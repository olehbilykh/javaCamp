package com.olehbilykh.camp.TaskForTransportHierarchy;

import java.util.Arrays;
import java.util.Objects;

/**
 * Для ієрархії класів:
 * Транспортний засіб => Літак, Автомобіль, Корабель;
 * Літак => Пасажирський літак, Транспортний літак, Винищувач,
 * Автомобіль => Легковий автомобіль, Автобус, Вантажний автомобіль,
 * Корабель => Пасажирський лайнер, Буксир, Танкер –
 * оголосити в кожному класі 1-3 найбільш доцільних поля
 */
public class Main {
    public static void main(String[] args) {

    }
}

class Vehicle {
    //    Map<String, Driver> roleAndDriver; main/assistant/remote driver maybe...
    static int totalVehiclesNumber;
    private int id;
    private int speed;
    private int productionYear;
    private int numberOfSeats; // 0 for drones

    Vehicle() {
        totalVehiclesNumber++;
    }

    public Vehicle(int id, int speed, int productionYear, int numberOfSeats) {
        this.id = id;
        this.speed = speed;
        this.productionYear = productionYear;
        this.numberOfSeats = numberOfSeats;
        totalVehiclesNumber++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", speed=" + speed +
                ", productionYear=" + productionYear +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && speed == vehicle.speed && productionYear == vehicle.productionYear && numberOfSeats == vehicle.numberOfSeats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, speed, productionYear, numberOfSeats);
    }
}

class Plane extends Vehicle {
    private double maxLoadWeight; // people+luggage/cargo weight/missiles etc.
    private double maxFlyingAltitude;

    public double getMaxLoadWeight() {
        return maxLoadWeight;
    }

    public void setMaxLoadWeight(double maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }

    public double getMaxFlyingAltitude() {
        return maxFlyingAltitude;
    }

    public void setMaxFlyingAltitude(double maxFlyingAltitude) {
        this.maxFlyingAltitude = maxFlyingAltitude;
    }

    public Plane(double maxLoadWeight, double maxFlyingAltitude) {
        this.maxLoadWeight = maxLoadWeight;
        this.maxFlyingAltitude = maxFlyingAltitude;
    }

    public Plane(int id, int speed, int productionYear, int numberOfSeats, double maxLoadWeight, double maxFlyingAltitude) {
        super(id, speed, productionYear, numberOfSeats);
        this.maxLoadWeight = maxLoadWeight;
        this.maxFlyingAltitude = maxFlyingAltitude;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "maxLoadWeight=" + maxLoadWeight +
                ", maxFlyingAltitude=" + maxFlyingAltitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Double.compare(plane.maxLoadWeight, maxLoadWeight) == 0 && Double.compare(plane.maxFlyingAltitude, maxFlyingAltitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxLoadWeight, maxFlyingAltitude);
    }
}

class PassengerPlane extends Plane {
    private String airline;
    private String[] seatCategories;  // 1 class/2 class/eco etc.

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String[] getSeatCategories() {
        return seatCategories;
    }

    public void setSeatCategories(String[] seatCategories) {
        this.seatCategories = seatCategories;
    }

    public PassengerPlane(double maxLoadWeight, double maxFlyingAltitude, String airline, String[] seatCategories) {
        super(maxLoadWeight, maxFlyingAltitude);
        this.airline = airline;
        this.seatCategories = seatCategories;
    }

    public PassengerPlane(int id, int speed, int productionYear, int numberOfSeats, double maxLoadWeight, double maxFlyingAltitude, String airline, String[] seatCategories) {
        super(id, speed, productionYear, numberOfSeats, maxLoadWeight, maxFlyingAltitude);
        this.airline = airline;
        this.seatCategories = seatCategories;
    }

    @Override
    public String toString() {
        return "PassengerPlane{" +
                "airline='" + airline + '\'' +
                ", seatCategories=" + Arrays.toString(seatCategories) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PassengerPlane that = (PassengerPlane) o;
        return airline.equals(that.airline) && Arrays.equals(seatCategories, that.seatCategories);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), airline);
        result = 31 * result + Arrays.hashCode(seatCategories);
        return result;
    }
}

class CargoPlane extends Plane {
    private boolean frontLoading;

    public boolean isFrontLoading() {
        return frontLoading;
    }

    public void setFrontLoading(boolean frontLoading) {
        this.frontLoading = frontLoading;
    }

    public CargoPlane(double maxLoadWeight, double maxFlyingAltitude, boolean frontLoading) {
        super(maxLoadWeight, maxFlyingAltitude);
        this.frontLoading = frontLoading;
    }

    public CargoPlane(int id, int speed, int productionYear, int numberOfSeats, double maxLoadWeight, double maxFlyingAltitude, boolean frontLoading) {
        super(id, speed, productionYear, numberOfSeats, maxLoadWeight, maxFlyingAltitude);
        this.frontLoading = frontLoading;
    }

    @Override
    public String toString() {
        return "CargoPlane{" +
                "frontLoading=" + frontLoading +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CargoPlane that = (CargoPlane) o;
        return frontLoading == that.frontLoading;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), frontLoading);
    }
}

class AntiMissilesSystems {

}

class TargetCapturingSystem {
}

class FighterPlane extends Plane {
    private AntiMissilesSystems antiMissilesSystems;
    private TargetCapturingSystem targetCapturingSystem;

    public FighterPlane(double maxLoadWeight, double maxFlyingAltitude, AntiMissilesSystems antiMissilesSystems, TargetCapturingSystem targetCapturingSystem) {
        super(maxLoadWeight, maxFlyingAltitude);
        this.antiMissilesSystems = antiMissilesSystems;
        this.targetCapturingSystem = targetCapturingSystem;
    }

    public FighterPlane(int id, int speed, int productionYear, int numberOfSeats, double maxLoadWeight, double maxFlyingAltitude, AntiMissilesSystems antiMissilesSystems, TargetCapturingSystem targetCapturingSystem) {
        super(id, speed, productionYear, numberOfSeats, maxLoadWeight, maxFlyingAltitude);
        this.antiMissilesSystems = antiMissilesSystems;
        this.targetCapturingSystem = targetCapturingSystem;
    }

    protected AntiMissilesSystems getAntiMissilesSystems() {
        return antiMissilesSystems;
    }

    void setAntiMissilesSystems(AntiMissilesSystems antiMissilesSystems) {
        this.antiMissilesSystems = antiMissilesSystems;
    }

    protected TargetCapturingSystem getTargetCapturingSystem() {
        return targetCapturingSystem;
    }

    void setTargetCapturingSystem(TargetCapturingSystem targetCapturingSystem) {
        this.targetCapturingSystem = targetCapturingSystem;
    }

    @Override
    public String toString() {
        return "FighterPlane{" +
                "antiMissilesSystems=" + antiMissilesSystems +
                ", targetCapturingSystem=" + targetCapturingSystem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FighterPlane that = (FighterPlane) o;
        return antiMissilesSystems.equals(that.antiMissilesSystems) && targetCapturingSystem.equals(that.targetCapturingSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), antiMissilesSystems, targetCapturingSystem);
    }
}

class Car extends Vehicle {
    private int numberOfWheels;
    private String gearBox;

    public Car(int numberOfWheels, String gearBox) {
        this.numberOfWheels = numberOfWheels;
        this.gearBox = gearBox;
    }

    public Car(int id, int speed, int productionYear, int numberOfSeats, int numberOfWheels, String gearBox) {
        super(id, speed, productionYear, numberOfSeats);
        this.numberOfWheels = numberOfWheels;
        this.gearBox = gearBox;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public String getGearBox() {
        return gearBox;
    }

    public void setGearBox(String gearBox) {
        this.gearBox = gearBox;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numberOfWheels=" + numberOfWheels +
                ", gearBox='" + gearBox + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return numberOfWheels == car.numberOfWheels && gearBox.equals(car.gearBox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfWheels, gearBox);
    }
}

class PassengerCar extends Car {
    private boolean isCabriolet;

    public PassengerCar(int numberOfWheels, String gearBox, boolean isCabriolet) {
        super(numberOfWheels, gearBox);
        this.isCabriolet = isCabriolet;
    }

    public PassengerCar(int id, int speed, int productionYear, int numberOfSeats, int numberOfWheels, String gearBox, boolean isCabriolet) {
        super(id, speed, productionYear, numberOfSeats, numberOfWheels, gearBox);
        this.isCabriolet = isCabriolet;
    }

    public boolean isCabriolet() {
        return isCabriolet;
    }

    public void setCabriolet(boolean cabriolet) {
        isCabriolet = cabriolet;
    }

    @Override
    public String toString() {
        return "PassengerCar{" +
                "isCabriolet=" + isCabriolet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PassengerCar that = (PassengerCar) o;
        return isCabriolet == that.isCabriolet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isCabriolet);
    }
}

class CargoCar extends Car {
    private double maxLoadWeight;

    public CargoCar(int numberOfWheels, String gearBox) {
        super(numberOfWheels, gearBox);
    }

    public CargoCar(int id, int speed, int productionYear, int numberOfSeats, int numberOfWheels, String gearBox) {
        super(id, speed, productionYear, numberOfSeats, numberOfWheels, gearBox);
    }

    public double getMaxLoadWeight() {
        return maxLoadWeight;
    }

    public void setMaxLoadWeight(double maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }

    @Override
    public String toString() {
        return "CargoCar{" +
                "maxLoadWeight=" + maxLoadWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CargoCar cargoCar = (CargoCar) o;
        return Double.compare(cargoCar.maxLoadWeight, maxLoadWeight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxLoadWeight);
    }
}

class Bus extends Car {
    private double ticketPrice;

    public Bus(int numberOfWheels, String gearBox) {
        super(numberOfWheels, gearBox);
    }

    public Bus(int id, int speed, int productionYear, int numberOfSeats, int numberOfWheels, String gearBox) {
        super(id, speed, productionYear, numberOfSeats, numberOfWheels, gearBox);
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "ticketPrice=" + ticketPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bus bus = (Bus) o;
        return Double.compare(bus.ticketPrice, ticketPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ticketPrice);
    }
}

class Ship extends Vehicle {
    private boolean hasHelicopterAirField;
    private boolean isPirate;

    public Ship(boolean hasHelicopterAirField, boolean isPirate) {
        this.hasHelicopterAirField = hasHelicopterAirField;
        this.isPirate = isPirate;
    }

    public Ship(int id, int speed, int productionYear, int numberOfSeats, boolean hasHelicopterAirField, boolean isPirate) {
        super(id, speed, productionYear, numberOfSeats);
        this.hasHelicopterAirField = hasHelicopterAirField;
        this.isPirate = isPirate;
    }

    public boolean isHasHelicopterAirField() {
        return hasHelicopterAirField;
    }

    public void setHasHelicopterAirField(boolean hasHelicopterAirField) {
        this.hasHelicopterAirField = hasHelicopterAirField;
    }

    public boolean isPirate() {
        return isPirate;
    }

    public void setPirate(boolean pirate) {
        isPirate = pirate;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "hasHelicopterAirField=" + hasHelicopterAirField +
                ", isPirate=" + isPirate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return hasHelicopterAirField == ship.hasHelicopterAirField && isPirate == ship.isPirate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasHelicopterAirField, isPirate);
    }
}

class PassengerShip extends Ship {
    private boolean hasTheater;
    private boolean hasPool;

    public PassengerShip(boolean hasHelicopterAirField, boolean isPirate) {
        super(hasHelicopterAirField, isPirate);
    }

    public PassengerShip(int id, int speed, int productionYear, int numberOfSeats, boolean hasHelicopterAirField, boolean isPirate) {
        super(id, speed, productionYear, numberOfSeats, hasHelicopterAirField, isPirate);
    }

    public boolean isHasTheater() {
        return hasTheater;
    }

    public void setHasTheater(boolean hasTheater) {
        this.hasTheater = hasTheater;
    }

    public boolean isHasPool() {
        return hasPool;
    }

    public void setHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    @Override
    public String toString() {
        return "PassengerShip{" +
                "hasTheater=" + hasTheater +
                ", hasPool=" + hasPool +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PassengerShip that = (PassengerShip) o;
        return hasTheater == that.hasTheater && hasPool == that.hasPool;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hasTheater, hasPool);
    }
}

class Tugboat extends Ship {
    double maxPullingWeight;

    public Tugboat(boolean hasHelicopterAirField, boolean isPirate) {
        super(hasHelicopterAirField, isPirate);
    }

    public Tugboat(int id, int speed, int productionYear, int numberOfSeats, boolean hasHelicopterAirField, boolean isPirate) {
        super(id, speed, productionYear, numberOfSeats, hasHelicopterAirField, isPirate);
    }

    public double getMaxPullingWeight() {
        return maxPullingWeight;
    }

    public void setMaxPullingWeight(double maxPullingWeight) {
        this.maxPullingWeight = maxPullingWeight;
    }

    @Override
    public String toString() {
        return "Tugboat{" +
                "maxPullingWeight=" + maxPullingWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tugboat tugboat = (Tugboat) o;
        return Double.compare(tugboat.maxPullingWeight, maxPullingWeight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxPullingWeight);
    }
}

class Tanker extends Ship {
    private double oilTankCapacity;

    public Tanker(boolean hasHelicopterAirField, boolean isPirate) {
        super(hasHelicopterAirField, isPirate);
    }

    public Tanker(int id, int speed, int productionYear, int numberOfSeats, boolean hasHelicopterAirField, boolean isPirate) {
        super(id, speed, productionYear, numberOfSeats, hasHelicopterAirField, isPirate);
    }

    public double getOilTankCapacity() {
        return oilTankCapacity;
    }

    public void setOilTankCapacity(double oilTankCapacity) {
        this.oilTankCapacity = oilTankCapacity;
    }

    @Override
    public String toString() {
        return "Tanker{" +
                "oilTankCapacity=" + oilTankCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tanker tanker = (Tanker) o;
        return Double.compare(tanker.oilTankCapacity, oilTankCapacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oilTankCapacity);
    }
}