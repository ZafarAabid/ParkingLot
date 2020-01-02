package com.parkinglot;

import java.sql.Time;
import java.util.Objects;

public class
ParkingSlot {
    public final Object vehicle;
    private final long vehicleParkingTime;

    public ParkingSlot(Object vehicle) {
        this.vehicleParkingTime = System.currentTimeMillis();
        this.vehicle = vehicle;
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
