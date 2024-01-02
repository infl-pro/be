package song.mall2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import song.mall2.exception.invalid.InvalidException;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.NotFoundException;
import song.mall2.exception.portone.PortoneException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Map<String, String>> notFoundExceptionHandler(NotFoundException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionMap);
    }

    @ExceptionHandler({PortoneException.class})
    public ResponseEntity<Map<String, String>> portoneExceptionHandler(PortoneException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionMap);
    }

    @ExceptionHandler({InvalidException.class})
    public ResponseEntity<Map<String, String>> invalidExceptionHandler(InvalidException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionMap);
    }
}
