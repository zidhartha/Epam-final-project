package com.epam.rd.autocode.assessment.appliances.Exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private String showError(Model model,
                             String titleKey,
                             String message,
                             HttpStatus status,
                             Locale locale) {

        model.addAttribute("title",
                messageSource.getMessage(titleKey, null, locale));

        // If messageKey is from messages.properties resolve normally
        // use validation method directly if provided basically
        model.addAttribute("message", message);

        model.addAttribute("status", status.value());
        model.addAttribute("timestamp", LocalDateTime.now());

        return "error/customError";
    }

    /// my custom errors
    @ExceptionHandler({
            OrderNotFoundException.class,
            ApplianceNotFoundException.class,
            ManufacturerNotFoundException.class,
            ClientNotFoundException.class,
            EmployeeNotFoundException.class
    })
    public String handleEntityNotFound(Exception ex, Model model, Locale locale) {

        log.error("Entity not found: {}", ex.getMessage());

        return showError(
                model,
                "error.notfound.title",
                messageSource.getMessage("error.notfound", null, locale),
                HttpStatus.NOT_FOUND,
                locale
        );
    }

    // validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex,
                                   Model model,
                                   Locale locale) {

        log.error("Validation failed: {}", ex.getMessage());

        // Extract first validation message
        String validationMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return showError(
                model,
                "error.validation.title",
                validationMessage,
                HttpStatus.BAD_REQUEST,
                locale
        );
    }

    // 409 database constraint errors
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDB(DataIntegrityViolationException ex,
                           Model model,
                           Locale locale) {

        log.error("Database constraint violation: {}", ex.getMessage());

        return showError(
                model,
                "error.db.title",
                messageSource.getMessage("error.db", null, locale),
                HttpStatus.CONFLICT,
                locale
        );
    }

    /*500 */
    @ExceptionHandler(Exception.class)
    public String handleUnexpected(Exception ex,
                                   Model model,
                                   Locale locale) {

        log.error("Unexpected error: {}", ex.getMessage(), ex);

        return showError(
                model,
                "error.unexpected.title",
                messageSource.getMessage("error.unexpected", null, locale),
                HttpStatus.INTERNAL_SERVER_ERROR,
                locale
        );
    }
    // favicon errors were annoying me so.
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleMissingFavicon(NoResourceFoundException ex) {
        if (ex.getResourcePath().equals("favicon.ico")) {
            return null;
        }
        log.error("Unexpected error: " + ex.getMessage());
        return "error";
    }

}
