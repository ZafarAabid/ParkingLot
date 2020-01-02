package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    Map<Integer, ParkingSlot> vehicleSlotMap;
    List<Integer> unOccupiedSlotList;

    ParkingLot(Integer parkingLotSize) {
        vehicleSlotMap = new HashMap<>();
        unOccupiedSlotList = new ArrayList<Integer>();
        for (int slotPositions = 0; slotPositions < parkingLotSize; slotPositions++) {
            unOccupiedSlotList.add(slotPositions);
        }
    }

    public void parkVehicle(Object vehicle) {
        vehicleSlotMap.put(unOccupiedSlotList.remove(0), new ParkingSlot(vehicle));
    }

    public void parkVehicle(Integer slotPosition, Object vehicle) {
        vehicleSlotMap.put(slotPosition, new ParkingSlot(vehicle));
        unOccupiedSlotList.remove(slotPosition);
    }

    public Integer vehicleLocation(Object vehicle) throws ParkingLotException {
        Integer position;
        try {
            position = vehicleSlotMap
                    .entrySet()
                    .stream()
                    .filter(a -> a.getValue().equals(vehicle))
                    .findFirst().orElse(null)
                    .getKey();
        }catch (NullPointerException e){ throw new ParkingLotException("Unparking wrong vehicle",ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE); }
        return position;
    }
}
