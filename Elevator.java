import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

class Elevator {
    private int capacity;
    private boolean isGoingUp;
    private int currentFloor;
    private Queue<Passenger> passengers;
    private int currentTick;
    public Elevator(int capacity) {
        this.capacity = capacity;
        this.currentFloor = 1;
        this.passengers = new LinkedList<>();
        this.isGoingUp = true;
        this.currentTick = 0;
    }

    public void unloadPassengers(int currentTick) {
        Iterator<Passenger> iterator = passengers.iterator();
        while (iterator.hasNext()) {
            Passenger passenger = iterator.next();
            if (passenger != null && passenger.getDestinationFloor() == currentFloor) {
                passenger.setConveyanceTick(currentTick);
                iterator.remove(); // Remove the passenger from the iterator
            }
        }
    }


    public void loadPassengers(Queue<Passenger> waitingPassengers) {
        if(!waitingPassengers.isEmpty()) {
            while(passengers.size() < capacity) {
                passengers.add(waitingPassengers.poll());
            }
        }

    }

    public void move() {
        if (isGoingUp) {
            currentFloor++;
        } else {
            currentFloor--;
        }

        // Switch direction if top/bottom floor
        if (currentFloor == ElevatorSimulation.NUM_FLOORS) {
            isGoingUp = false;
        } else if (currentFloor == 1) {
            isGoingUp = true;
        }
        currentTick++;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void handleNewPassengers(List<Floor> floors, List<Passenger> allPassengers, double passengerProbability, int currentTick) {
        Random random = new Random();

        // Check each floor for new passengers
        for (int floor = 1; floor <= ElevatorSimulation.NUM_FLOORS; floor++) {
            if (random.nextDouble() < passengerProbability) {
                // Create a new passenger
                Passenger passenger = floors.get(floor - 1).generatePassenger(currentTick);
                allPassengers.add(passenger);
                passengers.offer(passenger);

            }
        }
    }



}
