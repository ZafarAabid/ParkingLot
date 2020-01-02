package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem;
    Object vehicle;
    ParkingLotOwner owner;
    AirportSecurity security = new AirportSecurity();

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(5);
        vehicle = new Object();
        owner = new ParkingLotOwner();
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldUnparked() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            Object isVehicleParked = parkingLotSystem.unPark(vehicle);
            Assert.assertNotNull(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_shouldReturnFalse() {
        try {
            Object isVehicleParked = parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type,ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE);
        }
    }

    @Test
    public void givenParkingLot_WhenAttemptToUnparkDifferentVehicle_shouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            Object isVehicleParked = parkingLotSystem.unPark(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type,ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
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
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertTrue(owner.isParkingLotFull());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformAirportSecurity() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.registerObserver(security);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertTrue(security.isParkingLotFull());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetEmptyAfterFull_ShouldInformOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
            parkingLotSystem.park(new Object());
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) { }
        boolean lotFull = owner.isParkingLotEmpty();
        Assert.assertFalse(lotFull);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkCar() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotHavingAttendant_IfVehicleIsNtParked_ShouldReturnFalse() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(new Object());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IfDemandedForSlot_shouldBeAbleToGetEmptySlotList() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        parkingLotSystem.registerObserver(owner);
        ArrayList emptySlots = (ArrayList) parkingLotSystem.findEmptySlots();
        Integer slotPosition = (int) (Math.random() * emptySlots.size());
        Assert.assertNotNull(slotPosition);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkInDecidedSlot() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
            parkingLotSystem.registerObserver(owner);
            ArrayList emptySlots = (ArrayList) parkingLotSystem.findEmptySlots();
            Integer slotPosition = (int) (Math.random() * emptySlots.size());
            parkingLotSystem.park(slotPosition,vehicle);
            Integer carParkedOnSlot = parkingLotSystem.findMyCar(vehicle);
            Assert.assertEquals(slotPosition,carParkedOnSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingSlots_ifVehicleParked_ShouldBeAbleToCharge() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle);
            Object returningVehicle =parkingLotSystem.unPark(vehicle);
            Assert.assertEquals(vehicle,returningVehicle);
        } catch (ParkingLotException e) {
        }
    }
}
