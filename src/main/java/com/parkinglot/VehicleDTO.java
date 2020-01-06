package com.parkinglot;

public class VehicleDTO {

    public Vehicle.VehicleType vehicleType;
    public String thisVehicleNumberPlate;
    public Vehicle.VehicleColor vehicleColor;
    public Integer slotPosition;
    public long vehicleParkingTime;

    public VehicleDTO(ParkingSlot parkingSlot) {

    this.thisVehicleNumberPlate = parkingSlot.vehicle.thisVehicleNumberPlate;
    this.vehicleColor = parkingSlot.vehicle.vehicleColor;
    this.vehicleType = parkingSlot.vehicle.vehicleType;
    this.slotPosition = parkingSlot.slotPosition;
    this.vehicleParkingTime = parkingSlot.vehicleParkingTime;
    }

}
