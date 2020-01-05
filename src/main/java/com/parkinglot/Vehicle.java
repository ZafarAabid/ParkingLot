package com.parkinglot;

public class Vehicle {

    public enum VehicleColor {WHITE,OTHER}       public VehicleColor vehicleColor;

    Vehicle(VehicleColor color)     { this.vehicleColor = color; }
}
