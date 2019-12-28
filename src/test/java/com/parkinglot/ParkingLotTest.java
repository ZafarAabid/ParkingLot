package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem;
    Object vehicle;
    ParkingLotOwner owner;
    AirportSecurity security = new AirportSecurity();


    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle = new Object();
        owner = new ParkingLotOwner();
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldReturnTrue() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldUnparked() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = false;
            isVehicleParked = parkingLotSystem.unPark(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_shouldReturnFalse() {
        boolean isVehicleParked = false;
        isVehicleParked = parkingLotSystem.unPark(vehicle);
    }

    @Test
    public void givenParkingLot_WhenAttemptToUnparkDifferentVehicle_shouldReturnFalse() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.unPark(new Object());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Such Type Vehicle Not Found", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldThrowException() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            Object vehicle2 = new Object();
            parkingLotSystem.park(vehicle2);
            parkingLotSystem.park(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformOwner() {
        Boolean lotFull;
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
            lotFull = owner.isParkingLotFull();
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            lotFull = owner.isParkingLotFull();
            Assert.assertTrue(lotFull);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformAirportSecurity() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.RegisterObserver(security);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            boolean lotFull = security.isParkingLotFull();
            Assert.assertTrue(lotFull);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetEmptyAfterFull_ShouldInformOwner() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
            parkingLotSystem.park(new Object());
        } catch (ParkingLotException e) {
        }
        parkingLotSystem.unPark(vehicle);
        boolean lotFull = owner.isParkingLotEmpty();
        Assert.assertFalse(lotFull);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkCar() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotHavingAttendant_IfVehicleIsNtParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(new Object());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkInDecidedSlot() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_HavingAttendant_WhenParkingLotFullShouldThrowException() {
        try {
            parkingLotSystem.RegisterObserver(owner);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full", e.getMessage());
        }
    }




}
