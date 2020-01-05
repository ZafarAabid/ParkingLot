package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ParkingLotTest {

    Vehicle vehicle;
    ParkingLotOwner owner;
    AirportSecurity security;

    @Before
    public void setUp() {
        vehicle = new Vehicle(Vehicle.VehicleColor.WHITE);
        owner = new ParkingLotOwner();
        security = new AirportSecurity();
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_shouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        Object vehicle1 = new Object();
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenParkingLot_WhenVehicleParked_shouldUnparked() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            Object isVehicleParked = parkingLotSystem.unPark(vehicle);
            Assert.assertEquals(null, isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenUnparkingUnavailableVehicle_shouldThrowVehicleNotFoundException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        Vehicle vehicle1 = new Vehicle(Vehicle.VehicleColor.WHITE);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new HandicapParkingStrategy());
            parkingLotSystem.park(vehicle1, new NormalParkingStrategy());
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_shouldReturnFalse() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE);
        }
    }

    @Test
    public void givenParkingLot_WhenAttemptToUnparkDifferentVehicle_shouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.unPark(new Vehicle(Vehicle.VehicleColor.WHITE));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            Vehicle vehicle2 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle2, new NormalParkingStrategy());
            parkingLotSystem.park(new Vehicle(Vehicle.VehicleColor.WHITE), new NormalParkingStrategy());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
        } catch (ParkingLotException e) {
            Assert.assertTrue(owner.isParkingLotFull());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetFull_ShouldInformAirportSecurity() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.registerObserver(security);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
        } catch (ParkingLotException e) {
            Assert.assertTrue(security.isParkingLotFull());
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLotGetEmptyAfterFull_ShouldInformOwner() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(new Vehicle(Vehicle.VehicleColor.WHITE), new NormalParkingStrategy());
            parkingLotSystem.park(new Vehicle(Vehicle.VehicleColor.WHITE), new NormalParkingStrategy());
            parkingLotSystem.unPark(vehicle);
        } catch (ParkingLotException e) {
        }
        boolean lotFull = owner.isParkingLotEmpty();
        Assert.assertFalse(lotFull);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkCar() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotHavingAttendant_IfVehicleIsNtParked_ShouldReturnFalse() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean isVehicleParked = parkingLotSystem.isVehicleParked(new Vehicle(Vehicle.VehicleColor.WHITE));
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_IfDemandedForSlot_shouldBeAbleToGetEmptySlotList() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 5);
        parkingLotSystem.registerObserver(owner);
        ArrayList emptySlots = (ArrayList) parkingLotSystem.findEmptySlots();
        Integer slotPosition = (int) (Math.random() * emptySlots.size());
        Assert.assertNotNull(slotPosition);
    }

    @Test
    public void givenParkingLot_HavingAttendant_shouldBeAbleToParkInDecidedSlot() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 5);
            parkingLotSystem.registerObserver(owner);
            ArrayList emptySlots = (ArrayList) parkingLotSystem.findEmptySlots();
            Integer slotPosition = (int) (Math.random() * emptySlots.size());
            Vehicle vehicle1 = new Vehicle(Vehicle.VehicleColor.WHITE);
            Vehicle vehicle2 = new Vehicle(Vehicle.VehicleColor.WHITE);
            Vehicle vehicle3 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle1, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle2, new NormalParkingStrategy());
            parkingLotSystem.park(vehicle3, new NormalParkingStrategy());
            VehicleLocation vehicleLocation = parkingLotSystem.findMyCar(vehicle);
            Assert.assertEquals((Integer) 0, vehicleLocation.parkinglot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingSlots_ifVehicleParked_ShouldBeAbleToCharge() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 5);
        try {
            parkingLotSystem.registerObserver(owner);
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleParkingLots_ifVehicleComes_ShouldUseEvenDistributionForParking() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 4);
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            VehicleLocation pos1 = parkingLotSystem.findMyCar(vehicle);

            Vehicle vehicle2 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle2, new HandicapParkingStrategy());
            VehicleLocation pos2 = parkingLotSystem.findMyCar(vehicle2);

            Vehicle vehicle3 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle3, new NormalParkingStrategy());
            VehicleLocation pos3 = parkingLotSystem.findMyCar(vehicle3);

            Vehicle vehicle4 = new Vehicle(Vehicle.VehicleColor.OTHER);
            parkingLotSystem.park(vehicle4, new NormalParkingStrategy());
            VehicleLocation pos4 = parkingLotSystem.findMyCar(vehicle4);


            Assert.assertEquals((Integer) 0, pos1.parkinglot);
            Assert.assertEquals((Integer) 0, pos1.parkingSlot);
            Assert.assertEquals((Integer) 0, pos2.parkinglot);
            Assert.assertEquals((Integer) 1, pos2.parkingSlot);
            Assert.assertEquals((Integer) 1, pos3.parkinglot);
            Assert.assertEquals((Integer) 0, pos3.parkingSlot);
            Assert.assertEquals((Integer) 2, pos4.parkinglot);
            Assert.assertEquals((Integer) 0, pos4.parkingSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleParkingLots_ifLargeVehicleComes_ShouldParkAtLast() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 4);
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());
            VehicleLocation pos1 = parkingLotSystem.findMyCar(vehicle);

            Vehicle vehicle2 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle2, new HandicapParkingStrategy());
            VehicleLocation pos2 = parkingLotSystem.findMyCar(vehicle2);

            Vehicle vehicle3 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle3, new LargeVehicleParkingStrategy());
            VehicleLocation pos3 = parkingLotSystem.findMyCar(vehicle3);

            Vehicle vehicle4 = new Vehicle(Vehicle.VehicleColor.OTHER);
            parkingLotSystem.park(vehicle4, new LargeVehicleParkingStrategy());
            VehicleLocation pos4 = parkingLotSystem.findMyCar(vehicle4);


            Assert.assertEquals((Integer) 0, pos1.parkinglot);
            Assert.assertEquals((Integer) 0, pos1.parkingSlot);
            Assert.assertEquals((Integer) 0, pos2.parkinglot);
            Assert.assertEquals((Integer) 1, pos2.parkingSlot);
            Assert.assertEquals((Integer) 0, pos3.parkinglot);
            Assert.assertEquals((Integer) 3, pos3.parkingSlot);
            Assert.assertEquals((Integer) 1, pos4.parkinglot);
            Assert.assertEquals((Integer) 3, pos4.parkingSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenMultipleParkingLotsWithCars_IfWhiteCarFound_ShouldReturnVehicle() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 4);
        parkingLotSystem.registerObserver(owner);
        try {
            parkingLotSystem.park(vehicle, new NormalParkingStrategy());

            Vehicle vehicle2 = new Vehicle(Vehicle.VehicleColor.OTHER);
            parkingLotSystem.park(vehicle2, new HandicapParkingStrategy());

            Vehicle vehicle3 = new Vehicle(Vehicle.VehicleColor.WHITE);
            parkingLotSystem.park(vehicle3, new LargeVehicleParkingStrategy());

            Vehicle vehicle4 = new Vehicle(Vehicle.VehicleColor.OTHER);
            parkingLotSystem.park(vehicle4, new LargeVehicleParkingStrategy());

            ArrayList<ParkingSlot> listOfVehicles = parkingLotSystem.findCarsWithColor(Vehicle.VehicleColor.WHITE);
            Assert.assertEquals(vehicle,listOfVehicles.get(0).vehicle);
            Assert.assertEquals(vehicle3,listOfVehicles.get(1).vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
