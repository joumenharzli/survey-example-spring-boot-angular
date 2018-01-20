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
