import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Floor {
    private int floorNumber;
    private Queue<Passenger> upQueue;
    private Queue<Passenger> downQueue;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.upQueue = new LinkedList<>();
        this.downQueue = new LinkedList<>();

    }


    public void load(Elevator elevator) {
        // Load passengers onto the elevator based on its direction
        Queue<Passenger> appropriateQueue = (elevator.getCurrentFloor() < floorNumber) ? upQueue : downQueue;
        elevator.loadPassengers(appropriateQueue);
    }

    public Passenger generatePassenger(int currentTick) {
        // Generate a new passenger with a random destination different from the current floor
        Random random = new Random();
        int arrivalTick = currentTick; // Store the current tick separately
        int destination;

        do {
            destination = random.nextInt(ElevatorSimulation.NUM_FLOORS) + 1;
        } while (destination == floorNumber);

        Passenger newPassenger = new Passenger(arrivalTick, destination);

//        // Print statements to debug
//        System.out.println("Generated Passenger: " + newPassenger);
//        System.out.println("Adding Passenger to " + (destination > floorNumber ? "Up" : "Down") + " Queue of Floor " + floorNumber);

        // Add the passenger to the appropriate queue
        if (destination > floorNumber) {
            upQueue.add(newPassenger);
        } else {
            downQueue.add(newPassenger);
        }

        return newPassenger;
    }

}
