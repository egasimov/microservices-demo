package az.company.event.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * @Author QasimovEY on 19.04.21
 */

@Getter
public class RecordNotFoundException extends CommonException {

    private List<Object> messageArguments;

    public RecordNotFoundException(String message, List<Object> messageArguments) {
        super(HttpStatus.NOT_FOUND.value(), message);
        this.messageArguments = messageArguments;
    }

    public static RecordNotFoundException of(String message, List<Object> messageArguments) {
        return new RecordNotFoundException(message, messageArguments);
    }

    public static RecordNotFoundException of(String message) {
        return new RecordNotFoundException(message, Collections.emptyList());
    }

    public Object[] messageArguments() {
        return messageArguments != null ? messageArguments.toArray() : new Object[0];
    }

}
