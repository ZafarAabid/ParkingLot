package com.parkinglot;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private ArrayList<ParkingLot> parkingLots;
    private int noOfLots;
    private int parkingLotCapacity;
    public ParkingLot parkingLot;
    List<ParkingLotObserver> parkingLotObserver;

    public ParkingLotSystem(int noOfLots, int parkingLotCapacity) {
        parkingLotObserver = new ArrayList();
        parkingLot = new ParkingLot(0, parkingLotCapacity);
        parkingLots = new ArrayList<ParkingLot>();
        this.parkingLotCapacity = parkingLotCapacity * noOfLots;

        this.noOfLots = noOfLots;
        IntStream.range(0, noOfLots).forEach(slotNumber -> this.parkingLots.add(new ParkingLot(slotNumber, parkingLotCapacity)));
    }

    public void registerObserver(ParkingLotObserver owner) {
        parkingLotObserver.add(owner);
    }

    void park(Vehicle vehicle, ParkingStrategy strategy) throws ParkingLotException {
        AtomicReference<Integer> totalSlotOccupied = new AtomicReference<>(0);
        parkingLots.stream().forEach(parkingLot1 -> totalSlotOccupied.updateAndGet(v -> v + parkingLot1.noOfVehicleParked));
        if (totalSlotOccupied.get() == parkingLotCapacity * noOfLots) {
            for (ParkingLotObserver observer : parkingLotObserver)
                observer.parkingLotIsFull();
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        }
        parkingLots = strategy.parkVehicle(parkingLots, vehicle);
    }


    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingLots.stream().anyMatch(parkingLot -> parkingLot.isVehiclePark(vehicle));
    }
   /* public void park(ParkingLot slot, Object vehicle) throws ParkingLotException {
        if (parkingLots.size() * slot.unOccupiedSlotList.size() == parkingLotCapacity*noOfLots)
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
        parkingLot.parkVehicle(slot, vehicle);
    }*/

    public Object unPark(Vehicle vehicle) throws ParkingLotException {
        try {
            VehicleLocation myCar = findMyCar(vehicle);
            ParkingLot parkingLot = parkingLots.stream()
                    .filter(parkingLot1 ->
                            parkingLot1.thisParkingLotNumber == myCar.parkinglot)
                    .findFirst()
                    .get();
            parkingLot.noOfVehicleParked--;
            return (parkingLot.listOfOccupiedSlots.stream().filter(slot -> slot.slotPosition == myCar.parkingSlot).findFirst().get()).vehicle = null;

        } catch (IndexOutOfBoundsException e) {
            throw new ParkingLotException("no vehicle found", ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE);
        }
    }


    public VehicleLocation findMyCar(Vehicle vehicle) throws ParkingLotException {
        VehicleLocation location = new VehicleLocation();
        Integer noOfSlots = parkingLots.stream().findFirst().get().listOfOccupiedSlots.size();
            for (Integer slotNumber = 0; slotNumber < noOfSlots; slotNumber++)
                for (ParkingLot lot : parkingLots)
                    if ( lot.listOfOccupiedSlots.get(slotNumber).vehicle == vehicle ) {
                         location.parkingSlot = slotNumber;
                         location.parkinglot = lot.thisParkingLotNumber;
                        return location;
                    }
        throw new ParkingLotException("No Such Vehicle Available", ParkingLotException.ExceptionType.UNPARKING_WRONG_VEHICLE);
    }

    public ArrayList<VehicleDTO> findCarsWithColor(Vehicle.VehicleColor vehicleColor, Vehicle.VehicleType vehicleType)  {
        ArrayList<ParkingSlot> slotArrayList = new ArrayList<>();
        for (ParkingLot lot: parkingLots ) {
            List<ParkingSlot> parkingSlotList  = (lot.listOfOccupiedSlots).stream()
                        .filter(slot -> slot.vehicle != null
                                && slot.vehicle.vehicleColor == vehicleColor && slot.vehicle.vehicleType == vehicleType)
                        .collect(Collectors.toList());
                slotArrayList.addAll(parkingSlotList);
            }
        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();
        slotArrayList.stream().forEach(slot -> vehicleDTOS.add(new VehicleDTO(slot)));
        return vehicleDTOS;
    }


    public List findEmptySlots() {
        return parkingLot.listOfOccupiedSlots;
    }
}
