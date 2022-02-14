package br.com.ermig.personal_financial.controllers;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

@UtilityClass
public class ConstraintValidation {
    
    public static <T, R> Mono<R> validate(T input, Function<T, Mono<R>> function) {
        Set<ConstraintViolation<T>> violations = getViolations(input);
        return violations.isEmpty() ? function.apply(input) : createBadRequest(violations);
    }

    private static <T> Set<ConstraintViolation<T>> getViolations(T input) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(input);
    }

    private static <T, R> Mono<R> createBadRequest(Set<ConstraintViolation<T>> violations) {
        String errors = violations.stream()
            .map(violation -> String.format("%s %s", StringUtils.capitalize(violation.getPropertyPath().toString()), violation.getMessage()))
            .sorted()
            .collect(Collectors.joining("|"));
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, errors));
    }

}
