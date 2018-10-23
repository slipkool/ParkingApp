package co.com.ceiba.parking;

import co.com.ceiba.parking.domain.entity.Vehicle;
import co.com.ceiba.parking.exception.VehicleException;
import co.com.ceiba.parking.service.impl.ParkingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingServiceUnitTest {
    private static final String EUD_945 = "EUD945";
    private static final Vehicle VEHICLE_TEST_1 = new Vehicle(1, "Moto", EUD_945, LocalDateTime.now(), 250);
    private static final Vehicle VEHICLE_TEST_2 = new Vehicle(4, "Moto", "ADC345", LocalDateTime.now(), 200);
    private static final Vehicle VEHICLE_TEST_3 = new Vehicle(2, "Carro", "RIF846", LocalDateTime.now(), 150);

    @Mock
    private ParkingRepository parkingRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVehicles(){
        List<Vehicle> vehiclesMock = new ArrayList<>();
        vehiclesMock.add(VEHICLE_TEST_1);
        vehiclesMock.add(VEHICLE_TEST_2);
        vehiclesMock.add(VEHICLE_TEST_3);
        when(parkingRepository.findVehiclesEnable()).thenReturn(vehiclesMock);

        List<Vehicle> vehicles = parkingService.getAllVehicles();

        Assert.assertEquals(3, vehicles.size());
    }

    @Test
    public void testGetVehicleByLicenceNumber(){
        Optional<Vehicle> vehicleMock = Optional.of(VEHICLE_TEST_1);
        when(parkingRepository.findByLicenceNumber(Mockito.anyString())).thenReturn(vehicleMock);

        Optional<Vehicle> vehicle = parkingService.getVehicleByLicenceNumber(EUD_945);

        if(vehicle.isPresent())
            Assert.assertEquals(EUD_945, vehicle.get().getLicenceNumber());
        else
            fail();
    }

    @Test
    public void testGetVehicleByLicenceNumberNotFound(){
        Optional<Vehicle> vehicleMock = Optional.empty();
        when(parkingRepository.findByLicenceNumber(Mockito.anyString())).thenReturn(vehicleMock);

        Optional<Vehicle> vehicle = parkingService.getVehicleByLicenceNumber(EUD_945);

        assertFalse(vehicle.isPresent());
    }

    @Test
    public void testAddVehicle() throws VehicleException {
        when(parkingRepository.findByLicenceNumber(Mockito.anyString())).thenReturn(Optional.empty());
        when(parkingRepository.save(VEHICLE_TEST_1)).thenReturn(VEHICLE_TEST_1);

        Vehicle vehicle = parkingService.addVehicle(VEHICLE_TEST_1);

        assertEquals(VEHICLE_TEST_1, vehicle);
    }

    @Test(expected = VehicleException.class)
    public void testAddVehicleVehicleExist() throws VehicleException {
        Optional<Vehicle> vehicleMock = Optional.of(VEHICLE_TEST_1);
        when(parkingRepository.findByLicenceNumber(Mockito.anyString())).thenReturn(vehicleMock);

        parkingService.addVehicle(VEHICLE_TEST_1);
    }

    @Test
    public void testDeleteVehicle() throws VehicleException {
        Optional<Vehicle> vehicleMock = Optional.of(VEHICLE_TEST_1);
        when(parkingRepository.findByLicenceNumber(Mockito.anyString())).thenReturn(vehicleMock);
        Mockito.doNothing().when(parkingRepository).deleteVehicle(Mockito.anyString());

        Optional<Vehicle> vehicle = parkingService.deleteVehicle(EUD_945);

        assertEquals(vehicleMock, vehicle);
    }

    @Test(expected = VehicleException.class)
    public void testDeleteVehicleNoExist() throws VehicleException {
        Optional<Vehicle> vehicleMock = Optional.empty();
        when(parkingRepository.findByLicenceNumber(Mockito.anyString())).thenReturn(vehicleMock);

        parkingService.deleteVehicle(EUD_945);
    }

    @Test
    public void testUpdateVehicle(){
        when(parkingRepository.save(Mockito.any(Vehicle.class))).thenReturn(VEHICLE_TEST_1);

        Vehicle vehicle = parkingService.updateVehicle(VEHICLE_TEST_1);

        assertEquals(VEHICLE_TEST_1, vehicle);
    }
}
