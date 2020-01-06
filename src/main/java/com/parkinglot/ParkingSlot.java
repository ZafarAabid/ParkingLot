package com.parkinglot;

import java.util.Objects;

public class
ParkingSlot {
    public Integer slotPosition;
    public Vehicle vehicle;
    public long vehicleParkingTime;

    public ParkingSlot(Integer slotPosition, Vehicle vehicle) {
        this.vehicleParkingTime = System.currentTimeMillis();
        this.vehicle = vehicle;
        this.slotPosition = slotPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this.vehicle == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return vehicleParkingTime == that.vehicleParkingTime &&
                Objects.equals(vehicle, that.vehicle);
    }

}
