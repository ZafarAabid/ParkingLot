package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    Map<Integer,Object> vehicleSlotMap;
    List<Integer> unOccupiedSlotList;

    ParkingLot(Integer parkingLotSize){
        vehicleSlotMap = new HashMap<>();
        unOccupiedSlotList = new ArrayList<Integer>();
        for (int slotPositions = 0; slotPositions < parkingLotSize; slotPositions++) {
            unOccupiedSlotList.add(slotPositions);
        }
    }

    public void parkVehicle( Object vehicle) {
        vehicleSlotMap.put(unOccupiedSlotList.remove(0),vehicle);
    }

    public void parkVehicle(Integer slotPosition, Object vehicle) {
        vehicleSlotMap.put(slotPosition,vehicle);
        unOccupiedSlotList.remove(slotPosition);
    }
}
