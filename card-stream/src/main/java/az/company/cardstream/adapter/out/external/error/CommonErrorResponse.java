package az.company.cardstream.adapter.out.external.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @Author QasimovEY on 13.05.21
 */
@Data
@NoArgsConstructor
public class CommonErrorResponse {
    private  String id;
    private  Integer code;
    private  String message;

    public CommonErrorResponse(String id, Integer code, String message) {
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public CommonErrorResponse(String id, HttpStatus status, String message) {
        this.id = id;
        this.code = status.value();
        this.message = message;
    }
}

