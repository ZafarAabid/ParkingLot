package com.parkinglot;

public class ParkingLotOwner implements ParkingLotObserver{

    private Boolean parkingLotIsFull =false;

    @Override
    public void parkingLotIsFull() {
        parkingLotIsFull = true;
    }

    @Override
    public void parkingLotIsEmpty() {
        parkingLotIsFull = false;
    }

    public Boolean isParkingLotEmpty() {
        return parkingLotIsFull;
    }

    public Boolean isParkingLotFull() {
        return parkingLotIsFull;
    }
}
