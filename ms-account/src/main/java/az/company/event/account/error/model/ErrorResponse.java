package az.company.event.account.error.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author MammadovMB on 1/13/2021
 */

@Getter
@ToString
public class ErrorResponse {
    private final String id;
    private final Integer code;
    private final String message;

    public ErrorResponse(String id, Integer code, String message) {
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String id, HttpStatus status, String message) {
        this.id = id;
        this.code = status.value();
        this.message = message;
    }

}
