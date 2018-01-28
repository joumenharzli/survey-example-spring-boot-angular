/*
 * Copyright (C) 2018 Joumen Harzli
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.github.joumenharzli.surveypoc.web.error;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 *
 * @author Joumen Harzli
 */
@ControllerAdvice
public class RestExceptionTranslator {

  private final MessageSource messageSource;

  public RestExceptionTranslator(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Handle Question Not Found
   *
   * @return 404 status with message telling that the question not found
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = QuestionNotFoundException.class)
  @ResponseBody
  public RestErrorDto handleQuestionNotFound(QuestionNotFoundException exception) {
    String errorCode = RestErrorConstants.ERR_QUESTIONS_NOT_FOUND_ERROR;
    return new RestErrorDto(errorCode, getLocalizedMessageFromErrorCode(errorCode, new Object[]{exception.getNotFoundQuestionsIds()}));
  }

  /**
   * Handle User Not Found
   *
   * @return 404 status with message telling that the user not found
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = UserNotFoundException.class)
  @ResponseBody
  public RestErrorDto handleUserNotFound(UserNotFoundException exception) {
    String errorCode = RestErrorConstants.ERR_USERS_NOT_FOUND_ERROR;
    return new RestErrorDto(errorCode, getLocalizedMessageFromErrorCode(errorCode, new Object[]{exception.getNotFoundUsersIds()}));
  }

  /**
   * Handle validation errors
   *
   * @return validation error with the fields errors and a bad request
   */
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public RestFieldsErrorsDto handleValidationExceptions(MethodArgumentNotValidException exception) {
    BindingResult result = exception.getBindingResult();

    String errorCode = RestErrorConstants.ERR_VALIDATION_ERROR;

    RestFieldsErrorsDto restFieldsErrors = new RestFieldsErrorsDto(errorCode, getLocalizedMessageFromErrorCode(errorCode));

    List<FieldError> fieldErrors = result.getFieldErrors();
    fieldErrors.forEach(fieldError ->
        restFieldsErrors.addError(new RestFieldErrorDto(fieldError.getField(), fieldError.getCode(),
            getLocalizedMessageFromFieldError(fieldError))));

    return restFieldsErrors;

  }

  /**
   * Handle all types of errors
   *
   * @return internal server error
   */
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public RestErrorDto handleAllExceptions() {
    String errorCode = RestErrorConstants.ERR_INTERNAL_SERVER_ERROR;
    return new RestErrorDto(errorCode, getLocalizedMessageFromErrorCode(errorCode));
  }

  /**
   * Get the correspondent localized message for a field error
   *
   * @param fieldError error that will be used for search
   * @return the localized message if found or the default one
   */
  private String getLocalizedMessageFromFieldError(FieldError fieldError) {
    return messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
  }

  /**
   * Get the correspondent localized message for an error code
   *
   * @param errorCode error that will be used for search
   * @return the localized message if found
   */
  private String getLocalizedMessageFromErrorCode(String errorCode) {
    return getLocalizedMessageFromErrorCode(errorCode, new Object[]{});
  }

  /**
   * Get the correspondent localized message for an error code
   *
   * @param errorCode error that will be used for search
   * @param arguments parameters that will be used when parsing the message
   * @return the localized message if found
   */
  private String getLocalizedMessageFromErrorCode(String errorCode, Object[] arguments) {
    return messageSource.getMessage(errorCode, arguments, LocaleContextHolder.getLocale());
  }
}
