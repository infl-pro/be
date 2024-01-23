package song.mall2.domain.common.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
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

@Slf4j
@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ResponseApi<String> notFoundExceptionHandler(NotFoundException exception) {
        return new ResponseApi<>(HttpStatus.NOT_FOUND.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidException.class})
    public ResponseApi<String> invalidExceptionHandler(InvalidException exception) {
        return new ResponseApi<>(HttpStatus.BAD_REQUEST.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalException.class})
    public ResponseApi<String> IllegalExceptionHandler(IllegalException exception) {
        return new ResponseApi<>(HttpStatus.NOT_ACCEPTABLE.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseApi<String> argumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return new ResponseApi<>(HttpStatus.BAD_REQUEST.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({PortoneException.class})
    public ResponseApi<String> portoneExceptionHandler(PortoneException exception) {
        return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({MailException.class})
    public ResponseApi<String> mailExceptionHandler(MailException exception) {
        return new ResponseApi<>(HttpStatus.SERVICE_UNAVAILABLE.value(), exception.getClass(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseApi<String> exceptionHandler(Exception exception) {
        return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getClass(), exception.getMessage());
    }
}
