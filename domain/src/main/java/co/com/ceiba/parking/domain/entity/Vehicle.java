package co.com.ceiba.parking.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="vehicle")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(name = "type_vehicle")
    @Getter
    private String typeVehicle;

    @Column(name = "licence_number")
    @Getter
    private String licenceNumber;

    @Column(name = "in_date")
    @Getter @Setter
    private LocalDateTime inDate;

    @Column(name = "out_date")
    @Getter @Setter
    private LocalDateTime outDate;

    @Column(name = "cylinder_capacity")
    @Getter
    private int cylinderCapacity;

    @Column(name = "rate")
    @Getter @Setter
    private int rate;

    public Vehicle(long id, String typeVehicle, String licenceNumber, LocalDateTime inDate, int cylinderCapacity){
        this.id = id;
        this.typeVehicle = typeVehicle;
        this.licenceNumber = licenceNumber;
        this.inDate = inDate;
        this.cylinderCapacity = cylinderCapacity;
    }
}
