package co.com.ceiba.parking.model;

import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Response {

    private int status;
    private String message;
}
