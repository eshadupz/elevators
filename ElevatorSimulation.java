import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


class ElevatorSimulation {
    public static final int NUM_FLOORS = 10; // Default value, replace with the actual number of floors
    private List<Floor> floors;
    private List<Elevator> elevators;
    private List<Passenger> allPassengers;
    private int tick;
    private int duration;
    private double passengerProbability;
    private String structures;

    public ElevatorSimulation(Properties properties) {
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();
        this.allPassengers = new ArrayList<>();
        this.tick = 0;

        // Check if properties is null before using it
        if (properties != null) {
            // Read the duration property from the file, with a default value from default.properties
            this.duration = Integer.parseInt(properties.getProperty("duration"));
            this.passengerProbability = Double.parseDouble(properties.getProperty("passengers"));
            int capacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));

            // Initialize floors and elevators based on property values
            initializeElevators(properties);
            initializeFloors(properties);

        } else {
            // Handle the case where properties is null (e.g., file not found)
            System.out.println("Error: Properties are null. Exiting program.");
            System.exit(1);
        }
    }

    private int initializeElevators(Properties properties) {
        int numElevators = Integer.parseInt(properties.getProperty("elevators"));
        int capacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));

        for(int i = 0; i < numElevators; i++) {
            Elevator elevator = new Elevator(capacity);
            elevators.add(elevator);
        }

        return numElevators;
    }

    private int initializeFloors(Properties properties) {
        int numFloors = Integer.parseInt(properties.getProperty("floors"));

        for(int i = 0; i < numFloors; i++) {
            Floor floor = new Floor(i);
            floors.add(floor);
        }

        return numFloors;
    }

    public void runSimulation() {
        while (tick < duration) {

            // Handle elevator unload & load
            handleElevatorUnloadLoad();

            // Handle elevator travel
            moveElevators();

            // Handle new passengers
            handleNewPassengers();
            //debug
//            printQueueContents();


            // Increment tick after performing simulation steps
            tick++;
        }

        // Report results
        reportResults();
    }

//debug
//    private void printQueueContents() {
//        for (Floor floor : floors) {
//            floor.printQueueContents();
//        }
//    }
    private void handleElevatorUnloadLoad() { //for all the elevators, run the unloadPassengers and load any passengers on the current floor
        for (Elevator elevator : elevators) {
            elevator.unloadPassengers(tick);
            floors.get(elevator.getCurrentFloor() - 1).load(elevator);
        }
    }

    private void moveElevators() { //for all the elevators in the properties file, run the move function
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }

    private void handleNewPassengers() {//
        for (Elevator elevator : elevators) {
            elevator.handleNewPassengers(floors, allPassengers, passengerProbability, tick);
        }
    }

    private void reportResults() {
        System.out.println("Simulation ended. Reporting results:");

        // Calculate and report average, longest, and shortest conveyance times
        int totalConveyanceTime = 0;
        int longestConveyanceTime = Integer.MIN_VALUE;
        int shortestConveyanceTime = Integer.MAX_VALUE;

        for (Passenger passenger : allPassengers) {
            if (passenger.getConveyanceTick() != -1) { // Passenger conveyed
                int conveyanceTime = passenger.getConveyanceTick() - passenger.getArrivalTick();
                totalConveyanceTime += conveyanceTime;

                if (conveyanceTime > longestConveyanceTime) {
                    longestConveyanceTime = conveyanceTime;
                }

                if (conveyanceTime < shortestConveyanceTime) {
                    shortestConveyanceTime = conveyanceTime;
                }
            }
        }

        int totalPassengersConveyed = allPassengers.size(); // Including those not conveyed

        if (totalPassengersConveyed > 0) {
            System.out.println("Average Conveyance Time: " + (totalConveyanceTime / totalPassengersConveyed));
        } else {
            System.out.println("No passengers were conveyed.");
        }

        System.out.println("Longest Conveyance Time: " + longestConveyanceTime);
        System.out.println("Shortest Conveyance Time: " + shortestConveyanceTime);
    }


}
