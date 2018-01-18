package com.github.joumenharzli.surveypoc.exception;

import java.util.List;

/**
 * User Not Found Exception
 *
 * @author Joumen HARZLI
 */
public class UserNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Users with ids %s was not found";

  private final List<Long> notFoundUsersIds;

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param notFoundUsersIds the ids of the not found users
   */
  public UserNotFoundException(List<Long> notFoundUsersIds) {
    super(String.format(ERROR_MESSAGE, notFoundUsersIds));
    this.notFoundUsersIds = notFoundUsersIds;
  }

  /**
   * @return ids of the not found users
   */
  public List<Long> getNotFoundUsersIds() {
    return notFoundUsersIds;
  }
}
