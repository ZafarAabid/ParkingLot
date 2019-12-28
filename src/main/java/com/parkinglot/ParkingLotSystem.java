package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ParkingLotSystem {
    private int parkingLotCapacity;
    List<ParkingLotObserver> parkingLotObserver;
    public ParkingLot parkingLot = new ParkingLot();
    Integer parkingSlot=0;

    public ParkingLotSystem(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        parkingLotObserver = new ArrayList();
    }

    public void RegisterObserver(ParkingLotObserver owner) {
        parkingLotObserver.add(owner);
    }


    void park(Object vehicle) throws ParkingLotException {
        if (parkingLot.vehicleSlotMap.size() == this.parkingLotCapacity) {
            for (ParkingLotObserver observer : parkingLotObserver)
                observer.parkingLotIsFull();
            throw new ParkingLotException("Parking lot is full");
        }
        parkingLot.parkVehicle(parkingSlot,vehicle);
        parkingSlot++;
    }

    public boolean unPark(Object vehicle) {
        if (parkingLot.vehicleSlotMap.size() == 0) return false;
        if (parkingLot.vehicleSlotMap.containsValue(vehicle)) {
            parkingLot.vehicleSlotMap.remove(vehicle);
            for (ParkingLotObserver observer : parkingLotObserver)
                observer.parkingLotIsEmpty();
            return true;
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (parkingLot.vehicleSlotMap.containsValue(vehicle))
            return true;
        return false;
    }

    public Integer findMyCar(Object vehicle) {
        for ( Integer position: parkingLot.vehicleSlotMap.keySet() ) {
            if (parkingLot.vehicleSlotMap.get(position).equals(vehicle))
                return position;
        }
        return null;
    }
}
