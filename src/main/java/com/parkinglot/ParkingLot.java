package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLot {
    Map<Integer, ParkingSlot> vehicleSlotMap;
    List<Integer> unOccupiedSlotList;

    ParkingLot(Integer parkingLotSize) {
        vehicleSlotMap = new HashMap<>();
        unOccupiedSlotList = new ArrayList<Integer>();
        IntStream.range(0, parkingLotSize).forEach(slotNumber -> this.unOccupiedSlotList.add(slotNumber));
    }

    public void parkVehicle(Object vehicle) {
        vehicleSlotMap.put(unOccupiedSlotList.remove(0), new ParkingSlot(this,vehicle));
    }

    public void parkVehicle(Integer slotPosition, Object vehicle) {
        vehicleSlotMap.put(slotPosition, new ParkingSlot(this,vehicle));
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
