package org.example.aprocmd.exception.command;

import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.common.ResponseContainer;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class CommandExceptionHandler {

    @ExceptionHandler(CommandNotFoundException.class)
    public Mono<ResponseContainer<?>> commandNotFoundException(CommandNotFoundException e) {
        ResponseContainer<?> response = ResponseContainer.emptyResponse();
        log.error("", e.getMessage());
        response.setError(e);
        return Mono.just(response);
    }

    @ExceptionHandler(RequestStartCommandCreateException.class)
    public Mono<ResponseContainer<?>> requestStartCommandCreateException(RequestStartCommandCreateException e) {
        ResponseContainer<?> response = ResponseContainer.emptyResponse();
        log.error("", e.getMessage());
        response.setError(e);
        return Mono.just(response);
    }
}
