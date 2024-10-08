package groom.study.week3.common.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //(basePackages = "groom.study.week3.common.exception")
public class CustomExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleException(CustomException e,
                                                               jakarta.servlet.http.HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        LOGGER.error("Advice 내 handleException 호출, {}, {}", request.getRequestURI(),
                e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", e.getHttpStatusType());
        map.put("code", Integer.toString(e.getHttpStatusCode()));
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException e,
                                                               jakarta.servlet.http.HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("Advice 내 exceptionHandler 호출, {}, {}", request.getRequestURI(),
                e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}