package com.parkinglot;

import java.util.ArrayList;

public interface ParkingStrategy {
   public ArrayList<ParkingLot> parkVehicle(ArrayList<ParkingLot> parkingLots, Object vehicle) throws ParkingLotException;
}
