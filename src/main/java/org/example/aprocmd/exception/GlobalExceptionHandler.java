package org.example.aprocmd.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.aprocmd.common.ResponseContainer;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseContainer<?>> illegalArgumentException(IllegalArgumentException e) {
        ResponseContainer<?> response = ResponseContainer.emptyResponse();
        log.error("", e.getMessage());
        response.setError(e);
        return Mono.just(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseContainer<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseContainer<?> response = ResponseContainer.emptyResponse();

        List<FieldErrorDetail> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorDetail(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        response.setError(fieldErrors);
        return Mono.just(response);
    }
}
