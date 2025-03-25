package org.example.aprocmd.exception.validation;

import lombok.Getter;
import org.example.aprocmd.exception.FieldErrorDetail;

import java.util.List;


public record ValidationErrorResponse(
        boolean success,
        List<FieldErrorDetail> errors,
        int statusCode
) {}
