package az.company.cardstream.adapter.out.external.exception;

import az.company.cardstream.adapter.out.external.error.CommonErrorResponse;
import feign.error.FeignExceptionConstructor;
import feign.error.ResponseBody;
import feign.error.ResponseHeaders;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@Getter
public class CommonClientException extends RuntimeException {
    private Integer code;
    private String message;

    public CommonClientException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @FeignExceptionConstructor
    public CommonClientException(@ResponseBody CommonErrorResponse errorResponse,
                                 @ResponseHeaders Map<String, Collection<String>> headers) {
        if (errorResponse != null) {
            this.code = errorResponse.getCode();
            this.message = errorResponse.getMessage();
        }
    }
}
