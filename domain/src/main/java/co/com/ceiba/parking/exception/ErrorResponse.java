package co.com.ceiba.parking.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {

    private int errorCode;
    private String message;
}
