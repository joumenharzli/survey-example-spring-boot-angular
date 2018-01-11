package com.github.joumenharzli.surveypoc.web.error;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Translate exceptions to {@link RestErrorsDTO}
 *
 * @author Joumen HARZLI
 */
@ControllerAdvice
public class RestExceptionTranslator {

  /**
   * Handle validation errors
   *
   * @return validation error with the fields errors and a bad request
   */
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public RestErrorsDTO handleValidationExceptions(MethodArgumentNotValidException exception) {
    BindingResult result = exception.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();

    return new RestErrorsDTO(
        new RestErrorDTO(RestErrorConstants.ERR_VALIDATION_ERROR,
            "Validation Error", fieldErrors));

  }

  /**
   * Handle all types of errors
   *
   * @return internal server error
   */
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public RestErrorsDTO handleAllExceptions() {

    return new RestErrorsDTO(
        new RestErrorDTO(RestErrorConstants.ERR_INTERNAL_SERVER_ERROR,
            "Internal Server Error"));

  }
}
