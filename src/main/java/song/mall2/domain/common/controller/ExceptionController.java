package song.mall2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import song.mall2.exception.illegal.IllegalException;
import song.mall2.exception.invalid.InvalidException;
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
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionMap);
    }

    @ExceptionHandler({InvalidException.class})
    public ResponseEntity<Map<String, String>> invalidExceptionHandler(InvalidException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionMap);
    }

    @ExceptionHandler({IllegalException.class})
    public ResponseEntity<Map<String, String>> IllegalExceptionHandler(IllegalException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exceptionMap);
    }

    @ExceptionHandler({PortoneException.class})
    public ResponseEntity<Map<String, String>> portoneExceptionHandler(PortoneException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exceptionMap.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionMap);
    }

    @ExceptionHandler({MailException.class})
    public ResponseEntity<Map<String, String>> mailExceptionHandler(MailException exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exceptionMap.put("message", "이메일 전송에 실패했습니다.");

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(exceptionMap);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception exception) {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("type", exception.getClass().getSimpleName());
        exceptionMap.put("message", "알수없는 오류가 발생했습니다.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionMap);
    }
}
