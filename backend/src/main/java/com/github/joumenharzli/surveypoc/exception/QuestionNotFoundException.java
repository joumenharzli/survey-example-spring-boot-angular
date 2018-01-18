package com.github.joumenharzli.surveypoc.exception;

import java.util.List;

/**
 * Question Not Found Exception
 *
 * @author Joumen HARZLI
 */
public class QuestionNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Questions with ids %s was not found";

  private final List<Long> notFoundQuestionsIds;

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param notFoundQuestionsIds the ids of the not found questions
   */
  public QuestionNotFoundException(List<Long> notFoundQuestionsIds) {
    super(String.format(ERROR_MESSAGE, notFoundQuestionsIds));
    this.notFoundQuestionsIds = notFoundQuestionsIds;
  }

  /**
   * @return ids of the not found questions
   */
  public List<Long> getNotFoundQuestionsIds() {
    return notFoundQuestionsIds;
  }
}
