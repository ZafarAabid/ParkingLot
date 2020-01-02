package com.parkinglot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    private int parkingLotCapacity;
    List<ParkingLotObserver> parkingLotObserver;
    public ParkingLot parkingLot;

    public ParkingLotSystem(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        parkingLotObserver = new ArrayList();
        parkingLot = new ParkingLot(parkingLotCapacity);
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
        parkingLot.parkVehicle(vehicle);
    }

    public void park(Integer slotPosition, Object vehicle) throws ParkingLotException {
        if (parkingLot.vehicleSlotMap.size() == this.parkingLotCapacity)
            throw new ParkingLotException("Parking lot is full");
        parkingLot.parkVehicle(slotPosition, vehicle);
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
        return parkingLot.vehicleSlotMap
                .entrySet()
                .stream()
                .filter(a -> a.getValue().equals(vehicle))
                .findFirst().orElse(null).getKey();
    }

    public List findEmptySlots() {
        return parkingLot.unOccupiedSlotList;
    }
}
