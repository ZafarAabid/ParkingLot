package com.parkinglot;

import java.util.ArrayList;
import java.util.Comparator;

public class HandicapParkingStrategy implements ParkingStrategy {
    @Override
    public ArrayList<ParkingLot> parkVehicle(ArrayList<ParkingLot> parkingLots, Object vehicle) {
        ParkingLot parkingLot1 = parkingLots.stream().sorted(Comparator.comparing(parkingLot2 -> parkingLot2.noOfVehicleParked)).findFirst().get();
        (parkingLot1.listOfOccupiedSlots.stream().filter(slot -> slot.vehicle == null).findFirst().get()).vehicle = vehicle;
        return parkingLots;
    }
}
