package co.com.ceiba.parking.model;

import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @Getter
    @Setter
    private int status;

    @Getter @Setter
    private String message;
}
