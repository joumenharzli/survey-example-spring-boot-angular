package com.github.joumenharzli.surveypoc.web.error;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.joumenharzli.surveypoc.exception.QuestionNotFoundException;
import com.github.joumenharzli.surveypoc.exception.UserNotFoundException;

/**
 * Translate exceptions to {@link RestErrorsDTO}
 *
 * @author Joumen HARZLI
 */
@ControllerAdvice
public class RestExceptionTranslator {

  private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionTranslator.class);

  /**
   * Handle validation errors
   *
   * @return validation error with the fields errors and a bad request
   */
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public RestErrorsDTO handleValidationExceptions(MethodArgumentNotValidException exception) {

    LOGGER.error("Method Argument Not Valid Exception", exception);

    BindingResult result = exception.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();

    return new RestErrorsDTO(
        new RestErrorDTO(RestErrorConstants.ERR_VALIDATION_ERROR,
            "Validation Error", fieldErrors));

  }

  /**
   * Handle Question Not Found
   *
   * @return 404 status with message telling that the question not found
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = QuestionNotFoundException.class)
  @ResponseBody
  public RestErrorsDTO handleQuestionNotFound(QuestionNotFoundException exception) {
    LOGGER.error("Question not found", exception);

    String errorMessage = "The question with id %s was not found";

    RestErrorsDTO restErrors = new RestErrorsDTO();

    exception.getNotFoundQuestionsIds().forEach(id ->
        restErrors.addError(new RestErrorDTO(RestErrorConstants.ERR_QUESTION_NOT_FOUND_ERROR,
            String.format(errorMessage, id)))
    );

    return restErrors;
  }

  /**
   * Handle User Not Found
   *
   * @return 404 status with message telling that the user not found
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = UserNotFoundException.class)
  @ResponseBody
  public RestErrorsDTO handleUserNotFound(UserNotFoundException exception) {
    LOGGER.error("User not found", exception);

    String errorMessage = "The user with id %s was not found";

    RestErrorsDTO restErrors = new RestErrorsDTO();

    exception.getNotFoundUsersIds().forEach(id ->
        restErrors.addError(new RestErrorDTO(RestErrorConstants.ERR_USER_NOT_FOUND_ERROR,
            String.format(errorMessage, id)))
    );

    return restErrors;
  }

  /**
   * Handle all types of errors
   *
   * @return internal server error
   */
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public RestErrorsDTO handleAllExceptions(Exception exception) {

    LOGGER.error("Internal Server Error", exception);

    return new RestErrorsDTO(
        new RestErrorDTO(RestErrorConstants.ERR_INTERNAL_SERVER_ERROR,
            "Internal Server Error"));

  }
}
