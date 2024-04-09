import Containers.Container;
import Ports.Port;
import Ships.Ship;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Port> ports = new ArrayList<>();
        ArrayList<Ship> ships = new ArrayList<>();
        ArrayList<Container> containers = new ArrayList<>();
        int quantitySimulation = Integer.parseInt(scanner.next());

        int currentSimulation = 0;
        while (currentSimulation < quantitySimulation) {

            try {
                int numberSimulation = Integer.parseInt(scanner.next());

                switch (numberSimulation) {
                    case 1: {
                        int idPort = Integer.parseInt(scanner.next());
                        int weightContainer = Integer.parseInt(scanner.next());

                        Container container;
                        String check = scanner.nextLine();
                        if (check.isBlank()) {
                            container = Container.createContainer(containers.size(), weightContainer);
                        } else {
                            char typeContainer = check.replace(" ", "").charAt(0);
                            container = Container.createContainer(containers.size(), weightContainer, typeContainer);
                        }

                        containers.add(container);
                        ports.get(idPort).getContainers().add(container);
                        break;
                    }
                    case 2: {
                        int idPort = Integer.parseInt(scanner.next());
                        int totalWeightCapacity = Integer.parseInt(scanner.next());
                        int maxNumberOfAllContainers = Integer.parseInt(scanner.next());
                        int maxNumberOfHeaveContainers = Integer.parseInt(scanner.next());
                        int maxNumberOfRefrigeratedContainers = Integer.parseInt(scanner.next());
                        int maxNumberOfLiquidContainers = Integer.parseInt(scanner.next());
                        double fuelConsumption = Double.parseDouble(scanner.next());

                        Ship ship = new Ship(ships.size(), ports.get(idPort), totalWeightCapacity, maxNumberOfAllContainers,
                                maxNumberOfHeaveContainers, maxNumberOfRefrigeratedContainers, maxNumberOfLiquidContainers,
                                fuelConsumption);

                        ships.add(ship);
                        break;
                    }
                    case 3: {
                        double x = Double.parseDouble(scanner.next());
                        double y = Double.parseDouble(scanner.next());

                        Port port = new Port(ports.size(), x, y);
                        ports.add(port);
                        break;
                    }
                    case 4: {
                        int idShip = Integer.parseInt(scanner.next());
                        int idContainer = Integer.parseInt(scanner.next());

                        Container container = containers.get(idContainer);
                        if (ships.get(idShip).load(container)) {
                            System.out.println("The container has been successfully loaded onto the ship!");
                        }
                        else {
                            System.out.println("The container isn't loaded on the ship!");
                        }
                        break;
                    }
                    case 5: {
                        int idShip = Integer.parseInt(scanner.next());
                        int idContainer = Integer.parseInt(scanner.next());

                        Container container = containers.get(idContainer);
                        if (ships.get(idShip).unLoad(container)) {
                            System.out.println("The container has been successfully unloaded from the ship!");
                        } else {
                            System.out.println("The container isn't unloaded from the ship!");
                        }
                        break;
                    }
                    case 6: {
                        int idShip = Integer.parseInt(scanner.next());
                        int idOtherPort = Integer.parseInt(scanner.next());

                        if (ships.get(idShip).sailTo(ports.get(idOtherPort))) {
                            System.out.println("The ship went to port with id " + idOtherPort);
                        }
                        else {
                            System.out.println("Not enough fuel!");
                        }
                        break;
                    }
                    case 7: {
                        int idShip = Integer.parseInt(scanner.next());
                        double amountFuel = Double.parseDouble(scanner.next());

                        ships.get(idShip).reFuel(amountFuel);
                        break;
                    }
                    default: {
                        System.out.println("Invalid number! Try again");
                        currentSimulation--;
                    }
                }
                currentSimulation++;
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
                currentSimulation--;
            }
        }

        System.out.println("\n");
        for (Port port: ports) {
            System.out.println(port);
        }
    }
}
