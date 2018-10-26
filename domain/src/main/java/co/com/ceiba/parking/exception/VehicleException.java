package co.com.ceiba.parking.exception;

public class VehicleException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }


    public VehicleException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public VehicleException(){
        super();
    }
}
