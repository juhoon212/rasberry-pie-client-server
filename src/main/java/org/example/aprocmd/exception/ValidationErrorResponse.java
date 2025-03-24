package org.example.aprocmd.exception;

import lombok.Getter;

import java.util.List;


public record ValidationErrorResponse(
        boolean success,
        List<FieldErrorDetail> errors,
        int statusCode
) {}
