package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pl.put.poznan.transformer.logic.UnknownTransformationException;
import pl.put.poznan.transformer.rest.dto.ErrorResponse;

/**
 * Converts exceptions thrown by the logic layer into JSON error responses.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Handles requests containing an unsupported transformation name.
     *
     * @param ex exception describing the unknown transformation
     * @return error message with HTTP 400
     */
    @ExceptionHandler(UnknownTransformationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnknownTransformation(UnknownTransformationException ex) {
        logger.info("Bad request: {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }
}
