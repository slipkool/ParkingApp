package co.com.ceiba.parking.rest;

import co.com.ceiba.parking.domain.entity.Vehicle;
import co.com.ceiba.parking.exception.VehicleException;
import co.com.ceiba.parking.model.Response;
import co.com.ceiba.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
public class ParkingController {

    @Autowired
    ParkingService vehicleService;

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/vehiclesFilter/{licenceNumber}")
    public ResponseEntity<List<Vehicle>> getVehiclesFilter(@PathVariable String licenceNumber){
        return new ResponseEntity<>(vehicleService.getVehiclesFilter(licenceNumber), HttpStatus.OK);
    }

    @GetMapping("/vehicles/{licenceNumber}")
    public ResponseEntity<Vehicle> getVehicleByLicenceNumber(@PathVariable String licenceNumber){
        Optional<Vehicle> vehicle = vehicleService.getVehicleByLicenceNumber(licenceNumber);
        if(!vehicle.isPresent())
            throw new VehicleException("Vehicle not found:" + licenceNumber);
        return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        if(vehicle.getId() > 0){
            throw new VehicleException("Vehicle malformed, id must not be defined");
        }
        return new ResponseEntity<>(vehicleService.addVehicle(vehicle), HttpStatus.OK);
    }

    @PatchMapping("/vehicles")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicleParam) throws VehicleException{
        Optional<Vehicle> vehicle = vehicleService.getVehicleByLicenceNumber(vehicleParam.getLicenceNumber());
        if(vehicle.isPresent()){
            Vehicle vehicleUpdate = vehicleService.updateVehicle(vehicleParam);
            return new ResponseEntity<>(vehicleUpdate, HttpStatus.OK);
        }else{
            throw new VehicleException("Vehicle to update does not exist");
        }
    }

    @DeleteMapping("/vehicles/{licenceNumber}")
    public ResponseEntity<Response> deleteVehicle(@PathVariable String licenceNumber) throws VehicleException {
        Optional<Vehicle> vehicle =  vehicleService.deleteVehicle(licenceNumber);

        if(vehicle.isPresent())
            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "Vehicle has been deleted"), HttpStatus.OK);
        else{
            throw new VehicleException("Vehicle not found:" + licenceNumber);
        }
    }
}
