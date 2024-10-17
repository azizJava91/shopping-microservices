package com.ms.myShop.exception;

import com.ms.myShop.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;


@RestControllerAdvice
@Slf4j
public class ValidationExceptionsCatch {

    private List<String> addNextErrorToFieldErrorList(List<String> fieldErrorlist, String nextError) {
        fieldErrorlist.add(nextError);
        return fieldErrorlist;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objectError).getField();
            if (errors.containsKey(fieldName)) {
                errors.put(fieldName, addNextErrorToFieldErrorList(errors.get(fieldName), objectError.getDefaultMessage()));
            } else {
                errors.put(fieldName, addNextErrorToFieldErrorList(new ArrayList<>(), objectError.getDefaultMessage()));
            }
        }

        errors.forEach((field, messageList) -> log.error("Field: {}, Error: {}", field, String.join(", ", messageList)));

                Response response = Response.builder()
                .message("error")
                .statusCode(400)
                .body(errors)
                .build();

        return response;

    }
}
