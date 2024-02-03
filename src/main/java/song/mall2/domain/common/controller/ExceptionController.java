package song.mall2.domain.common.controller;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.exception.illegal.IllegalException;
import song.mall2.exception.invalid.InvalidException;
import song.mall2.exception.notfound.NotFoundException;
import song.mall2.exception.portone.PortoneException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler({NotFoundException.class, InvalidException.class, IllegalException.class})
    public ResponseEntity<ResponseApi<String, String>> requestExceptionHandler(Exception e) {
        if (e instanceof NotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi<>(HttpStatus.NOT_FOUND.value(), e.getClass(), e.getMessage()));
        }
        if (e instanceof InvalidException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi<>(HttpStatus.BAD_REQUEST.value(), e.getClass(), e.getMessage()));
        }
        if (e instanceof IllegalException) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseApi<>(HttpStatus.NOT_ACCEPTABLE.value(), e.getClass(), e.getMessage()));
        }
        return ResponseEntity.badRequest().body(new ResponseApi<>(HttpStatus.BAD_REQUEST.value(), e.getClass(), e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseApi<String, Map<String, String>> argumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error->{
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return new ResponseApi<>(HttpStatus.BAD_REQUEST.value(), exception.getClass(), errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({PortoneException.class})
    public ResponseApi<String, String> portoneExceptionHandler(PortoneException exception) {
        return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({MailException.class})
    public ResponseApi<String, String> mailExceptionHandler(MailException exception) {
        return new ResponseApi<>(HttpStatus.SERVICE_UNAVAILABLE.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({JwtException.class})
    public ResponseApi<String, String> jwtExceptionHandler(JwtException exception) {
        return new ResponseApi<>(HttpStatus.UNAUTHORIZED.value(), exception.getClass(), "유효하지 않은 인증 토큰입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseApi<String, String> exceptionHandler(Exception exception) {
        return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getClass(), "알 수 없는 예외가 발생했습니다.");
    }
}
