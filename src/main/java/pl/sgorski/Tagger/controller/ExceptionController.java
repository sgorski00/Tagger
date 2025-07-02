package pl.sgorski.Tagger.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sgorski.Tagger.exception.AiParsingException;

@Log4j2
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleInvalidFormatException(HttpMessageNotReadableException e) {
        log.error("Invalid format: {}", e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid format: " + e.getMessage()
        );
        problemDetail.setTitle("Invalid Format");
        return problemDetail;
    }

    @ExceptionHandler(BindException.class)
    public ProblemDetail handleValidationException(BindException e) {
        log.error("Validation error: {}", e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Validation has failed: " + e.getMessage()
        );
        problemDetail.setTitle("Not valid data");
        return problemDetail;
    }

    @ExceptionHandler(AiParsingException.class)
    public ProblemDetail handleAiParsingException(AiParsingException e) {
        log.error("Ai parsing error: {}", e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Parsing has failed: " + e.getMessage()
        );
        problemDetail.setTitle("Could not parse response");
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        log.error("An error occurred: {}", e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
        problemDetail.setTitle("Unexpected Error");
        return problemDetail;
    }
}
