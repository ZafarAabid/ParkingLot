package com.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private int parkingLotCapacity;
    public ParkingLot parkingLot;
    List<ParkingLotObserver> parkingLotObserver;

    public ParkingLotSystem(int parkingLotCapacity) {
        parkingLotObserver = new ArrayList();
        parkingLot = new ParkingLot(parkingLotCapacity);
        this.parkingLotCapacity = parkingLotCapacity;
    }

    public void registerObserver(ParkingLotObserver owner) {
        parkingLotObserver.add(owner);
    }


    void park(Object vehicle) throws ParkingLotException {
        if (parkingLot.vehicleSlotMap.size() == this.parkingLotCapacity) {
            for (ParkingLotObserver observer : parkingLotObserver)
                observer.parkingLotIsFull();
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        parkingLot.parkVehicle(vehicle);
    }

    public void park(Integer slotPosition, Object vehicle) throws ParkingLotException {
        if (parkingLot.vehicleSlotMap.size() == this.parkingLotCapacity)
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        parkingLot.parkVehicle(slotPosition, vehicle);
    }

    public Object unPark(Object vehicle) throws ParkingLotException {
        Integer positionOfVehicle = parkingLot.vehicleLocation(vehicle);
        ParkingSlot slot = parkingLot.vehicleSlotMap.remove(positionOfVehicle);
        parkingLot.unOccupiedSlotList.add(positionOfVehicle);
        parkingLot.unOccupiedSlotList.sort(Integer::compareTo);
        return slot.vehicle;
    }

    public boolean isVehicleParked(Object vehicle) {
        try {
            parkingLot.vehicleLocation(vehicle);
        } catch (ParkingLotException e) {
            return false;
        }
        return true;
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
