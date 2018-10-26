package co.com.ceiba.parking.service.impl;

import co.com.ceiba.parking.ParkingRepository;
import co.com.ceiba.parking.co.com.ceiba.parking.constant.Parameters;
import co.com.ceiba.parking.domain.entity.Vehicle;
import co.com.ceiba.parking.exception.VehicleException;
import co.com.ceiba.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.toIntExact;

@Service
public class ParkingServiceImpl implements ParkingService {

    private static final int EXTRA_TAXE_BY_CC = 2000;
    private static final int CC_CONDITION = 500;
    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public List<Vehicle> getAllVehicles() {
        List vehicles = new ArrayList();
        parkingRepository.findVehiclesEnable().forEach(vehicles::add);
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesFilter(String licenceNumber) {
        List vehicles = new ArrayList();
        parkingRepository.findVehiclesFilter(licenceNumber).forEach(vehicles::add);
        return vehicles;
    }

    @Override
    public Optional<Vehicle> getVehicleByLicenceNumber(String licenceNumber) {
        return parkingRepository.findByLicenceNumber(licenceNumber);
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) throws VehicleException {
        Optional<Vehicle> vehicleRegistered = parkingRepository.findByLicenceNumber(vehicle.getLicenceNumber());
        if(!vehicleRegistered.isPresent()){
            if(isaVehicleAvailableToAddForEmptyPlace(vehicle) && isaVehicleAvailableToAddForLicenceNumber(vehicle)) {
                vehicle.setInDate(LocalDateTime.now());
                return parkingRepository.save(vehicle);
            }else{
                throw new VehicleException("No empty place in parking");
            }
        }else{
            throw new VehicleException("Vehicle already exists");
        }

    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        vehicle.setOutDate(LocalDateTime.now());
        vehicle.setRate(calculateRate(vehicle));

        if(vehicle.getCylinderCapacity() > CC_CONDITION)
            vehicle.setRate(vehicle.getRate() + EXTRA_TAXE_BY_CC);
        return parkingRepository.save(vehicle);
    }

    private int calculateRate(Vehicle vehicle) {
        LocalDateTime tempDateTime = LocalDateTime.from(vehicle.getInDate());
        long days = tempDateTime.until(vehicle.getOutDate(), ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );

        long hours = tempDateTime.until( vehicle.getOutDate(), ChronoUnit.HOURS);
        
        long rate = days * Parameters.RateParking.valueOf(vehicle.getTypeVehicle().toUpperCase() + "_DAY").getValue() + hours * Parameters.RateParking.valueOf(vehicle.getTypeVehicle().toUpperCase() + "_HOUR").getValue();
        if(rate == 0)
            rate = Parameters.RateParking.valueOf(vehicle.getTypeVehicle().toUpperCase() + "_HOUR").getValue();
        return toIntExact(rate);
    }

    @Override
    public Optional<Vehicle> deleteVehicle(String licenceNumber) throws VehicleException {
        Optional<Vehicle> vehicle = parkingRepository.findByLicenceNumber(licenceNumber);
        if(vehicle.isPresent()) {
            parkingRepository.deleteVehicle(licenceNumber);
            return vehicle;
        }else{
            throw new VehicleException("Vehicle not found:" + licenceNumber);
        }
    }

    public boolean isaVehicleAvailableToAddForEmptyPlace(Vehicle vehicle){
        List vehicles = new ArrayList();
        parkingRepository.findUnavailableSpotByTypeVehicle(vehicle.getTypeVehicle()).forEach(vehicles::add);
        return Parameters.SizeOfParkingSpot.valueOf(vehicle.getTypeVehicle().toUpperCase()).getValue() > vehicles.size();
    }

    public boolean isaVehicleAvailableToAddForLicenceNumber(Vehicle vehicle) throws VehicleException {
        boolean isAvailable = false;
        if(vehicle.getLicenceNumber().startsWith("A")){
            LocalDate currentDate = LocalDate.now();
            String currentDay = currentDate.getDayOfWeek().toString();
            if(DayOfWeek.SUNDAY.name().equalsIgnoreCase(currentDay) || DayOfWeek.MONDAY.name().equalsIgnoreCase(currentDay)){
                isAvailable = true;
            }else {
                throw new VehicleException("Vehicle is not authorized to enter.");
            }
        }else{
            isAvailable = true;
        }
        return isAvailable;
    }
}
