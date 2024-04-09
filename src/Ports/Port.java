package Ports;

import Interfaces.IPort;
import Ships.*;
import Containers.*;

import java.util.ArrayList;

public class Port implements IPort {
    private int ID;
    private final double X;
    private final double Y;
    private final ArrayList<Container> containers = new ArrayList<>();
    private final ArrayList<Ship> history = new ArrayList<>();
    private final ArrayList<Ship> currentShips = new ArrayList<>();

    public Port(int ID, double x, double y) {
        setID(ID);
        this.X = x;
        this.Y = y;
    }

    public static double calculationDistance(Port currentPort, Port other) {
        return Math.sqrt(Math.pow(currentPort.getX() - other.getX(), 2) + Math.pow(currentPort.getY() - other.getY(), 2));
    }

    @Override
    public void incomingShip(Ship ship) {
        currentShips.add(ship);

        if (!history.contains(ship)) {
            history.add(ship);
        }
    }

    @Override
    public void outgoingShip(Ship ship) {
        currentShips.remove(ship);
    }

    public boolean equals(Port port) {
        return this.ID == port.getID();
    }

    private void setID(int ID) {
        if (ID < 0) throw new IndexOutOfBoundsException("The id port can't be negative!");
        this.ID = ID;
    }


    public ArrayList<Container> getContainers() {
        return containers;
    }

    private double getX() {
        return X;
    }

    private double getY() {
        return Y;
    }

    private int getID() {
        return ID;
    }

    @Override
    public String toString() {
        ArrayList<String> basicId = new ArrayList<>();
        ArrayList<String> heaveId = new ArrayList<>();
        ArrayList<String> refrigeratedId = new ArrayList<>();
        ArrayList<String> liquidContainerId = new ArrayList<>();

        for (Container container : containers) {

            if (container instanceof BasicContainer) {
                basicId.add(String.valueOf(container.getID()));
            } else if (container instanceof RefrigeratedContainer) {
                refrigeratedId.add(String.valueOf(container.getID()));
            } else if (container instanceof LiquidContainer) {
                liquidContainerId.add(String.valueOf(container.getID()));
            } else {
                heaveId.add(String.valueOf(container.getID()));
            }
        }

        StringBuilder infoShip = new StringBuilder();
        for (Ship ship: currentShips) {
            infoShip.append(ship.toString());
        }

        return String.format("Port %d: (%.2f, %.2f)%s%s%s%s\n  %s", this.ID, this.X, this.Y,
                basicId.isEmpty() ? "" : "\n  BasicContainer: " + String.join(" ", basicId),
                heaveId.isEmpty() ? "" : "\n  HeaveContainer: " + String.join(" ", heaveId),
                refrigeratedId.isEmpty() ? "" : "\n  RefrigeratedContainer: " + String.join(" ", refrigeratedId),
                liquidContainerId.isEmpty() ? "" : "\n  LiquidContainer: " + String.join(" ", liquidContainerId),
                infoShip
        );
    }

}
