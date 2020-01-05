package com.parkinglot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

public class LargeVehicleParkingStrategy implements ParkingStrategy {
    @Override
    public ArrayList<ParkingLot> parkVehicle(ArrayList<ParkingLot> parkingLots, Vehicle vehicle) throws ParkingLotException {
        parkingLots.sort(Comparator.comparing(parkingLot -> parkingLot.noOfVehicleParked,Comparator.reverseOrder()));
        Integer noOfSlots = parkingLots.stream().findFirst().get().listOfOccupiedSlots.size();
        for (Integer slotNumber = 0; slotNumber < noOfSlots; slotNumber++)
            for (ParkingLot lot : parkingLots)
                if (lot.listOfOccupiedSlots.get(lot.listOfOccupiedSlots.size()-1-slotNumber).vehicle == null) {
                    lot.listOfOccupiedSlots.get(lot.listOfOccupiedSlots.size()-1-slotNumber).vehicle = vehicle;
                    return parkingLots;
                }
        throw new ParkingLotException("parkingLotFull",ParkingLotException.ExceptionType.PARKING_LOT_FULL);
    }
}
