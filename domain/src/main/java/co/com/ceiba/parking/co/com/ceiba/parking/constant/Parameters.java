package co.com.ceiba.parking.co.com.ceiba.parking.constant;

public final class Parameters {
    public enum SizeOfParkingSpot{
        MOTORCYCLE(10),
        CAR(20);

        private final int value;

        SizeOfParkingSpot(int value){ this.value = value; }

        public int getValue(){ return value; }
    }

    public enum RateParking{
        MOTORCYCLE_HOUR(1000),
        CAR_HOUR(500),
        MOTORCYCLE_DAY(4000),
        CAR_DAY(8000);

        private final int value;

        RateParking(int value){ this.value = value; }

        public int getValue(){ return value; }
    }
}
