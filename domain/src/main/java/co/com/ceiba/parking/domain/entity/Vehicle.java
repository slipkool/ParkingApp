package co.com.ceiba.parking.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "type_vehicle")
    String typeVehicle;

    @Column(name = "licence_number")
    String licenceNumber;

    @Column(name = "in_date")
    private LocalDateTime inDate;

    @Column(name = "out_date")
    private LocalDateTime outDate;

    @Column(name = "cylinder_capacity")
    private int cylinderCapacity;

    @Column(name = "rate")
    private int rate;

    public Vehicle(){};

    public Vehicle(String type, String licenceNumber){
        this.typeVehicle = type;
        this.licenceNumber = licenceNumber;
    }

    public Vehicle(long id, String typeVehicle, String licenceNumber, LocalDateTime inDate, int cylinderCapacity){
        this.id = id;
        this.typeVehicle = typeVehicle;
        this.licenceNumber = licenceNumber;
        this.inDate = inDate;
        this.cylinderCapacity = cylinderCapacity;
    }

    public long getId() {
        return id;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public LocalDateTime getInDate() {
        return inDate;
    }

    public void setInDate(LocalDateTime inDate) {
        this.inDate = inDate;
    }

    public LocalDateTime getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDateTime outDate) {
        this.outDate = outDate;
    }

    public int getCylinderCapacity() {
        return cylinderCapacity;
    }

    public void setCylinderCapacity(int cylinderCapacity) {
        this.cylinderCapacity = cylinderCapacity;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
