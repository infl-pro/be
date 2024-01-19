package song.mall2.domain.common.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import song.mall2.domain.common.api.ExceptionApi;
import song.mall2.exception.illegal.IllegalException;
import song.mall2.exception.invalid.InvalidException;
import song.mall2.exception.notfound.NotFoundException;
import song.mall2.exception.portone.PortoneException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionApi> notFoundExceptionHandler(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), exception.getMessage()));
    }

    @ExceptionHandler({InvalidException.class})
    public ResponseEntity<ExceptionApi> invalidExceptionHandler(InvalidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), exception.getMessage()));
    }

    @ExceptionHandler({IllegalException.class})
    public ResponseEntity<ExceptionApi> IllegalExceptionHandler(IllegalException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionApi> argumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), "입력 형식이 올바르지 않습니다."));
    }

    @ExceptionHandler({PortoneException.class})
    public ResponseEntity<ExceptionApi> portoneExceptionHandler(PortoneException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), exception.getMessage()));
    }

    @ExceptionHandler({MailException.class})
    public ResponseEntity<ExceptionApi> mailExceptionHandler(MailException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), exception.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionApi> exceptionHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionApi(false, exception.getClass().getSimpleName(), "알 수 없는 오류가 발생했습니다."));
    }
}
