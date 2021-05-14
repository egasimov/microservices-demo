package az.company.card.adapter.in.web.error;

import az.company.card.adapter.in.web.error.exception.InvalidInputException;
import az.company.card.adapter.in.web.error.exception.RecordNotFoundException;
import az.company.card.adapter.in.web.error.model.ErrorResponse;
import az.company.card.adapter.out.external.exception.CommonClientException;
import az.company.card.shared.util.WebUtils;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static net.logstash.logback.argument.StructuredArguments.v;

/**
 * @Author QasimovEY on 12.05.21
 */
@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private final WebUtils webUtils;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        addErrorLog(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "MethodArgumentTypeMismatchException");
        return new ErrorResponse(webUtils.getLogId(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Optional<String> violations = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(collectingAndThen(joining("; "), Optional::ofNullable));
        String errMsg = violations.orElse(ex.getMessage());
        addErrorLog(HttpStatus.BAD_REQUEST.value(), errMsg, "ConstraintViolationException");
        return new ErrorResponse(webUtils.getLogId(), HttpStatus.BAD_REQUEST.value(), errMsg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleAll(Exception ex) {
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ex);
        String errMsg = "Unexpected internal server error occurs";
        return new ErrorResponse(
                webUtils.getLogId(), HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg/*ex.getMessage()*/);
    }


    @ExceptionHandler({FeignException.class})
    public ResponseEntity handleFeignException(FeignException ex) {
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ex);
        String errMsg = "Unexpected internal server error occurs on External Client Side";
        var response = new ErrorResponse(
                webUtils.getLogId(), ex.status(),
                ex != null ? ex.getMessage() : "Client is unreachable");
        return ResponseEntity
                .status(ex.status())
                .body(response);
    }


    @ExceptionHandler({CommonClientException.class})
    public ResponseEntity handleCommonClient(CommonClientException ex) {
        addErrorLog(ex.getCode(), ex.getMessage(), ex);
        String errMsg = "Unexpected internal server error occurs on External Client Side";
        var response = new ErrorResponse(
                webUtils.getLogId(), ex.getCode(),
                ex != null ? ex.getMessage() : "Client is unreachable");
        return ResponseEntity
                .status(ex.getCode())
                .body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidInputException.class})
    public ErrorResponse handleInvalidInputException(InvalidInputException ex) {
        addErrorLog(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "InvalidInputException");
        return new ErrorResponse(
                webUtils.getLogId(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RecordNotFoundException.class})
    public ErrorResponse handleRecordNotFoundException(RecordNotFoundException ex) {
        addErrorLog(ex.getCode(), ex.getMessage(), "RecordNotFoundException");
        return new ErrorResponse(
                webUtils.getLogId(),
                ex.getCode(),
                (ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Optional<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> String.format("'%s' %s", x.getField(), x.getDefaultMessage()))
                .collect(collectingAndThen(joining("; "), Optional::ofNullable));
        addErrorLog(status.value(), errors.orElse(ex.getMessage()), ex);
        ErrorResponse errorResponse =
                new ErrorResponse(webUtils.getLogId(), status.value(), errors.orElse(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        addErrorLog(status.value(), error, "MissingServletRequestParameterException");
        ErrorResponse errorResponse = new ErrorResponse(webUtils.getLogId(), status.value(), error);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String error = "Request not readable";
        addErrorLog(status.value(), ex.getMessage(), "HttpMessageNotReadableException");
        ErrorResponse errorResponse = new ErrorResponse(webUtils.getLogId(), status.value(), error);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    private void addErrorLog(int errorCode, String errorMessage, Throwable ex) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}",
                errorCode, ex.getClass().getTypeName(), webUtils.getRequestUri(),
                v("elapsed_time", webUtils.getElapsedTime()), errorMessage, ex);
    }

    private void addErrorLog(int errorCode, String errorMessage, String exceptionType) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}",
                errorCode, exceptionType, webUtils.getRequestUri(),
                v("elapsed_time", webUtils.getElapsedTime()), errorMessage);
    }
}

