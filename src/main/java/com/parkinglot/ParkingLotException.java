package com.parkinglot;

public class ParkingLotException extends Exception {

    ExceptionType type;
    public ParkingLotException(String message, ExceptionType exceptionType) {
        super(message);
        this.type = exceptionType;
    }

    public enum ExceptionType {UNPARKING_WRONG_VEHICLE, NO_VEHICLE_FOUND, PARKING_LOT_FULL}
}
