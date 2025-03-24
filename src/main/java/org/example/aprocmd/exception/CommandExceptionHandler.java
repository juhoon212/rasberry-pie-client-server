package org.example.aprocmd.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.common.ResponseContainer;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommandExceptionHandler {

    @ExceptionHandler(CommandNotFoundException.class)
    public ResponseContainer<?> CommandNotFoundException(CommandNotFoundException e) {
        ResponseContainer<?> response = ResponseContainer.emptyResponse();
        log.error("", e.getMessage());
        response.setError(e);
        return response;
    }
}
