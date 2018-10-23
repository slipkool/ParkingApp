package co.com.ceiba.parking.co.com.ceiba.parking.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Parameters {
    public enum SizeOfParkingSpot{
        MOTO(10),
        CARRO(20);

        private final int value;

        SizeOfParkingSpot(int value){ this.value = value; }

        public int getValue(){ return value; }
    }

    public enum RateParking{
        MOTO_HOUR(1000),
        CARRO_HOUR(500),
        MOTO_DAY(4000),
        CARRO_DAY(8000);

        private final int value;

        RateParking(int value){ this.value = value; }

        public int getValue(){ return value; }
    }
}
