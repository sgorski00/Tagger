package pl.sgorski.Tagger.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sgorski.Tagger.exception.AiParsingException;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionController {

    private final MessageSource messageSource;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleInvalidFormatException(HttpMessageNotReadableException e) {
        log.error("Invalid format: {}", e.getMessage(), e);
        String msg = messageSource.getMessage("error.invalid.format.message", null, LocaleContextHolder.getLocale());
        String title = messageSource.getMessage("error.invalid.format.title", null, LocaleContextHolder.getLocale());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                msg + ": " + e.getMessage()
        );
        problemDetail.setTitle(title);
        return problemDetail;
    }

    @ExceptionHandler(BindException.class)
    public ProblemDetail handleValidationException(BindException e) {
        log.error("Validation error: {}", e.getMessage(), e);
        String msg = messageSource.getMessage("error.validation.message", null, LocaleContextHolder.getLocale());
        String title = messageSource.getMessage("error.validation.title", null, LocaleContextHolder.getLocale());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                msg + ": " + e.getAllErrors().getFirst().getDefaultMessage()
        );
        problemDetail.setTitle(title);
        return problemDetail;
    }

    @ExceptionHandler(AiParsingException.class)
    public ProblemDetail handleAiParsingException(AiParsingException e) {
        log.error("Ai parsing error: {}", e.getMessage(), e);
        String msg = messageSource.getMessage("error.ai.parsing.message", null, LocaleContextHolder.getLocale());
        String title = messageSource.getMessage("error.ai.parsing.title", null, LocaleContextHolder.getLocale());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                msg + ": " + e.getMessage()
        );
        problemDetail.setTitle(title);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        log.error("An error occurred: {}", e.getMessage(), e);
        String msg = messageSource.getMessage("error.generic.message", null, LocaleContextHolder.getLocale());
        String title = messageSource.getMessage("error.generic.title", null, LocaleContextHolder.getLocale());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                msg + ": " + e.getMessage()
        );
        problemDetail.setTitle(title);
        return problemDetail;
    }
}
