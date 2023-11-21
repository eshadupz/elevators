// Passenger class represents a passenger in the simulation.
class Passenger {
    private int arrivalTick;
    private int conveyanceTick;
    private int destinationFloor;

    public Passenger(int arrivalTick, int destinationFloor) {
        this.arrivalTick = arrivalTick;
        this.destinationFloor = destinationFloor;
        this.conveyanceTick = -1; // Set to -1 initially, indicating not yet conveyed
    }

    public int getArrivalTick() {
        return arrivalTick;
    }

    public int getConveyanceTick() {
        return conveyanceTick;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setConveyanceTick(int conveyanceTick) {
        this.conveyanceTick = conveyanceTick;
    }
}
