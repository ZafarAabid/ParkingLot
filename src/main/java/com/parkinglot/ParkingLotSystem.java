package com.parkinglot;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private ArrayList<ParkingLot> parkingLots;
    private int noOfLots;
    private int parkingLotCapacity;
    public ParkingLot parkingLot;
    List<ParkingLotObserver> parkingLotObserver;
    ParkingHandler parkingHandler = new ParkingHandler();

    public ParkingLotSystem(int noOfLots, int parkingLotCapacity) {
        parkingLotObserver = new ArrayList();
        parkingLot = new ParkingLot(parkingLotCapacity);
        parkingLots = new ArrayList<ParkingLot>();
        this.parkingLotCapacity = parkingLotCapacity * noOfLots;

        this.noOfLots = noOfLots;
        IntStream.range(0, noOfLots).forEach(slotNumber -> this.parkingLots.add(new ParkingLot(parkingLotCapacity)));
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
        parkingLots = parkingHandler.handleThisVehicle(parkingLots, vehicle);
    }

    public void park(Integer slotPosition, Object vehicle) throws ParkingLotException {
        if (parkingLot.vehicleSlotMap.size() == this.parkingLotCapacity)
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        parkingLot.parkVehicle(slotPosition, vehicle);
    }

    public Object unPark(Object vehicle) throws ParkingLotException {
        for (ParkingLot lot : parkingLots)
            for (ParkingSlot slot : lot.vehicleSlotMap.values()) {
                if (slot.equals(vehicle)) {
                    parkingLot = slot.parkingLot;
                    break;
                }
            }
        Integer positionOfVehicle = parkingLot.vehicleLocation(vehicle);
        ParkingSlot slot = parkingLot.vehicleSlotMap.remove(positionOfVehicle);
        parkingLot = slot.parkingLot;
        parkingLot.unOccupiedSlotList.add(positionOfVehicle);
        parkingLot.unOccupiedSlotList.sort(Integer::compareTo);
        for (ParkingLot thisParkingLot : parkingLots)
            if (thisParkingLot.equals(parkingLot))
                thisParkingLot = parkingLot;
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

    public int findMyCar(Object vehicle) throws ParkingLotException {
        Integer position = null;
        for (ParkingLot parkingLot : parkingLots) {
            position = parkingLot.vehicleSlotMap
                    .entrySet()
                    .stream()
                    .filter(a -> a.getValue().equals(vehicle))
                    .findFirst().orElse(null)
                    .getKey();
            if (position != null) break;
        }
        return position;
    }

    public List findEmptySlots() {
        return parkingLot.unOccupiedSlotList;
    }
}
