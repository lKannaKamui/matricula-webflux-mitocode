package com.miacademia.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component //administrar por spring
@Order(-1)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler { //manejar excepciones para enfoque anotaciones y funcional

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                               ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters()); //sobreescribir respuesta http
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), //interceptar todas las peticiones
                                                this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        Map<String, Object> generalError = getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
        Map<String, Object> customError = new HashMap<>();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        int statusCode = Integer.parseInt(String.valueOf(generalError.get("status")));
        Throwable error = getError(serverRequest);

        //switch mejorado, disponible desde Java 17
        switch (statusCode){
            case 400, 422 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 400);
                status = HttpStatus.BAD_REQUEST;
            }
            case 404 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 404);
                status = HttpStatus.NOT_FOUND;
            }
            case 401, 403 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 401);
                status = HttpStatus.UNAUTHORIZED;
            }
            case 500 -> {
                customError.put("message", error.getMessage());
                customError.put("status", 500);
            }
            default -> {
                customError.put("message", error.getMessage());
                customError.put("status", 418);
                status = HttpStatus.I_AM_A_TEAPOT;
            }
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customError));
    }
}
