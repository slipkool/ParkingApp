package co.com.ceiba.parking;

import co.com.ceiba.parking.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends CrudRepository<Vehicle, Long> {

    @Query("select v from Vehicle v where v.outDate is null")
    List<Vehicle> findVehiclesEnable();

    @Query("select v from Vehicle v where v.licenceNumber = :licenceNumber and v.outDate is null")
    Optional<Vehicle> findByLicenceNumber(@Param("licenceNumber") String licenceNumber);

    @Query("select v from Vehicle v where v.typeVehicle = :typeVehicle and v.outDate is null")
    List<Vehicle> findUnavailableSpotByTypeVehicle(@Param("typeVehicle") String typeVehicle);

    @Modifying
    @Transactional
    @Query("delete from Vehicle v where v.licenceNumber = :licenceNumber")
    void deleteVehicle(@Param("licenceNumber") String licenceNumber);
}
