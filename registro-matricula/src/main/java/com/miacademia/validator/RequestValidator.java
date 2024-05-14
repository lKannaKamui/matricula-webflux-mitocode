package com.miacademia.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T t){
        if(t == null){
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        }
        Set<ConstraintViolation<T>> constraints = validator.validate(t);

        if(constraints == null || constraints.isEmpty()){
            return Mono.just(t);
        }

        Optional<String> mensaje = constraints.stream().map(cv -> cv.getMessage()).findFirst();

        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje.get()));
    }
}
