package com.parkinglot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ParkingHandler {
    private ParkingLot parkingLot;

    public ArrayList handleThisVehicle(ArrayList<ParkingLot> parkingLots, Object vehicle) {
        ArrayList<ParkingLot> lotListOfHighestEmptySlots = parkingLots;
        Collections.sort(parkingLots, Comparator.comparing(list -> list.listOfOccupiedSlots.size(), Comparator.reverseOrder()));
        parkingLot = lotListOfHighestEmptySlots.get(0);
        parkingLot.parkVehicle(vehicle);
        for (ParkingLot thisParkingLot : parkingLots)
            if (thisParkingLot.equals(parkingLot))
                thisParkingLot = parkingLot;
    return parkingLots;
    }

}
