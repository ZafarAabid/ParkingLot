package com.parkinglot;

import java.util.List;

public class ParkingLotAttendant {

    private  Object vehicle;

        public ParkingLotAttendant() {
    }

    public void attendantToParkCar(ParkingLot parkingLot, int parkingSlot, Object vehicle) {
        parkingLot.ParkVehicle(parkingSlot,vehicle);
    }
}
