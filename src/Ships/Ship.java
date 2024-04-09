package Ships;

import Interfaces.IShip;
import Ports.Port;
import Containers.*;

import java.util.ArrayList;
import java.util.Collections;

public class Ship implements IShip {
    private int ID;

    private double fuel = 1000;

    private int totalWeightCapacity;

    private int maxNumberOfAllContainers;

    private int maxNumberOfHeaveContainers;

    private int maxNumberOfRefrigeratedContainers;

    private int maxNumberOfLiquidContainers;

    private double fuelConsumptionPerKM;

    private int currentTotalWeight = 0;

    private int currentNumberOfAllContainers = 0;

    private int currentNumberOfHeaveContainers = 0;

    private int currentNumberOfRefrigeratedContainers = 0;

    private int currentNumberOfLiquidContainers = 0;

    private Port currentPort;

    ArrayList<Container> containers = new ArrayList<>();

    public Ship(int ID, Port currentPort, int totalWeightCapacity, int maxNumberOfAllContainers,
                int maxNumberOfHeaveContainers, int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers,
                double fuelConsumptionPerKM) {
        setID(ID);

        this.currentPort = currentPort;
        currentPort.incomingShip(this);

        setTotalWeightCapacity(totalWeightCapacity);
        setMaxNumberOfAllContainers(maxNumberOfAllContainers);
        setMaxNumberOfHeaveContainers(maxNumberOfHeaveContainers);
        setMaxNumberOfRefrigeratedContainers(maxNumberOfRefrigeratedContainers);
        setMaxNumberOfLiquidContainers(maxNumberOfLiquidContainers);
        setFuelConsumptionPerKM(fuelConsumptionPerKM);
    }

    public double fuelConsumption (double distance) {
        double fuelRequired = fuelConsumptionPerKM * distance; // the fuel required for a ship without containers

        for (Container container : containers) {
            fuelRequired += container.consumption() * distance; // adding fuel spend from the containers
        }
        return fuelRequired;
    }

    @Override
    public boolean sailTo(Port port) {
        if (port.equals(currentPort)) throw new IllegalArgumentException("The ship is already in this port!");

        double distanceToOtherPort = Port.calculationDistance(currentPort, port);
        double fuelRequired = fuelConsumption(distanceToOtherPort);

        if (fuelRequired < fuel) {
            fuel -= fuelRequired;
            currentPort.outgoingShip(this);
            currentPort = port;
            currentPort.incomingShip(this);
            return true;
        }
        return false;
    }
    @Override
    public void reFuel(double newFuel) {
        if (newFuel < 0) throw new IndexOutOfBoundsException("The amount of fuel can't be negative!");
        fuel += newFuel;
    }
    @Override
    public boolean load(Container container) {

        boolean canLoad = false;
        boolean checkTotalWeight = container.getWeight() + currentTotalWeight <= totalWeightCapacity;
        boolean checkQuantityAllContainers = currentNumberOfAllContainers <= maxNumberOfAllContainers;
        if (currentPort.getContainers().contains(container) || checkTotalWeight || checkQuantityAllContainers) {

            if (container instanceof HeavyContainer) {
                if (currentNumberOfHeaveContainers > maxNumberOfHeaveContainers) {
                    return false;
                }

                if (container instanceof RefrigeratedContainer) {
                    canLoad = currentNumberOfRefrigeratedContainers <= maxNumberOfRefrigeratedContainers;
                }
                else if (container instanceof LiquidContainer) {
                    canLoad = currentNumberOfLiquidContainers <= maxNumberOfLiquidContainers;
                }
                else {
                    canLoad = true;
                }
            }
            else {
                canLoad = true;
            }
        }

        if (canLoad) {
            currentPort.getContainers().remove(container);
            containers.add(container);
            currentNumberOfAllContainers = containers.size();
            currentTotalWeight += container.getWeight();

            if (container instanceof HeavyContainer) {
                currentNumberOfHeaveContainers++;

                if (container instanceof RefrigeratedContainer) {
                    currentNumberOfRefrigeratedContainers++;
                }
                else if (container instanceof LiquidContainer) {
                    currentNumberOfLiquidContainers++;
                }
            }
        }
        return canLoad;
    }
    @Override
    public boolean unLoad(Container container) {
        boolean checkAvailability = containers.remove(container);

        if (checkAvailability) {
            currentPort.getContainers().add(container);
            currentTotalWeight -= container.getWeight();
            currentNumberOfAllContainers--;

            if (container instanceof HeavyContainer) {
                currentNumberOfHeaveContainers--;

                if (container instanceof RefrigeratedContainer) {
                    currentNumberOfRefrigeratedContainers--;
                }
                else if (container instanceof LiquidContainer) {
                    currentNumberOfLiquidContainers--;
                }
            }
        }
        return checkAvailability;
    }

    private void setID(int ID) {
        if (ID < 0) throw new IndexOutOfBoundsException("The id ship can't be negative!");
        this.ID = ID;
    }
    private void setTotalWeightCapacity(int totalWeightCapacity) {
        if (totalWeightCapacity <= 0) throw new IndexOutOfBoundsException("The total weight capacity can't be negative or null!");
        this.totalWeightCapacity = totalWeightCapacity;
    }
    private void setMaxNumberOfAllContainers(int maxNumberOfAllContainers) {
        if (maxNumberOfAllContainers < 0) throw new IndexOutOfBoundsException("The max number of all containers can't be negative!");
        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
    }
    private void setMaxNumberOfHeaveContainers(int maxNumberOfHeaveContainers) {
        if (maxNumberOfHeaveContainers > maxNumberOfAllContainers) throw new IndexOutOfBoundsException("Invalid argument!");
        if (maxNumberOfHeaveContainers < 0) throw new IndexOutOfBoundsException("The max number of heavy containers can't be negative!");
        this.maxNumberOfHeaveContainers = maxNumberOfHeaveContainers;
    }
    private void setMaxNumberOfRefrigeratedContainers(int maxNumberOfRefrigeratedContainers) {
        if (maxNumberOfRefrigeratedContainers > maxNumberOfHeaveContainers) throw new IndexOutOfBoundsException("Invalid argument!");
        if (maxNumberOfRefrigeratedContainers < 0) throw new IndexOutOfBoundsException("The max number of refrigerated containers can't be negative!");
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
    }
    private void setMaxNumberOfLiquidContainers(int maxNumberOfLiquidContainers) {
        if (maxNumberOfLiquidContainers > maxNumberOfHeaveContainers) throw new IndexOutOfBoundsException("Invalid argument!");
        if (maxNumberOfLiquidContainers < 0) throw new IndexOutOfBoundsException("The max number of liquid containers can't be negative!");
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
    }
    private void setFuelConsumptionPerKM(double fuelConsumptionPerKM) {
        if (fuelConsumptionPerKM <= 0) throw new IndexOutOfBoundsException("The fuel consumption can't be negative or null!");
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
    }

    public ArrayList<Container> getCurrentContainers() {
        ArrayList<Container> containersCopy = new ArrayList<>(containers);
        Collections.sort(containersCopy);
        return containersCopy;
    }

    @Override
    public String toString() {
        ArrayList<String> basicId = new ArrayList<>();
        ArrayList<String> heaveId = new ArrayList<>();
        ArrayList<String> refrigeratedId = new ArrayList<>();
        ArrayList<String> liquidContainerId = new ArrayList<>();

        for (Container container: this.getCurrentContainers()) {

            if (container instanceof BasicContainer) {
                basicId.add(String.valueOf(container.getID()));
            }
            else if (container instanceof RefrigeratedContainer) {
                refrigeratedId.add(String.valueOf(container.getID()));
            }
            else if (container instanceof LiquidContainer) {
                liquidContainerId.add(String.valueOf(container.getID()));
            }
            else {
                heaveId.add(String.valueOf(container.getID()));
            }
        }

        return String.format("Ship %d: %.2f%s%s%s%s\n  ", this.ID, this.fuel,
                basicId.isEmpty() ? "" : "\n    BasicContainer: " + String.join(" ", basicId),
                heaveId.isEmpty() ? "" : "\n    HeaveContainer: " + String.join(" ", heaveId),
                refrigeratedId.isEmpty() ? "" : "\n    RefrigeratedContainer: " + String.join(" ", refrigeratedId),
                liquidContainerId.isEmpty() ? "" : "\n    LiquidContainer: " + String.join(" ", liquidContainerId));
    }
}
