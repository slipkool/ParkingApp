package co.com.ceiba.parking.service;

import co.com.ceiba.parking.domain.entity.Vehicle;
import co.com.ceiba.parking.exception.VehicleException;

import java.util.List;
import java.util.Optional;

public interface ParkingService {
    List<Vehicle> getAllVehicles();
    Optional<Vehicle> getVehicleByLicenceNumber(String licenceNumber);
    Vehicle addVehicle(Vehicle vehicle) throws VehicleException;
    Vehicle updateVehicle(Vehicle vehicle);
    Optional<Vehicle> deleteVehicle(String licenceNumber) throws VehicleException;
}
