package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    Map<Integer,Object> vehicleSlotMap = new HashMap<>();

    public void parkVehicle(int parkingSlot, Object vehicle) {
        vehicleSlotMap.put(parkingSlot,vehicle);
    }
}
