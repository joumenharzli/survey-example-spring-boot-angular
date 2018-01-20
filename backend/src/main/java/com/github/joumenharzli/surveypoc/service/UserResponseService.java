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

package com.github.joumenharzli.surveypoc.service;

import java.util.List;

import com.github.joumenharzli.surveypoc.exception.QuestionNotFoundException;
import com.github.joumenharzli.surveypoc.exception.UserNotFoundException;
import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;

/**
 * User Response Service
 *
 * @author Joumen Harzli
 */
public interface UserResponseService {

  /**
   * Find the responses for the provided questions and user
   *
   * @param userId       id of the user who responded
   * @param questionsIds ids of the questions that the user may responded
   * @return list of responses of the user
   * @throws UserNotFoundException     if no user was found
   * @throws QuestionNotFoundException if no question was found
   * @throws IllegalArgumentException if any given argument is invalid
   */
  List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(Long userId, List<Long> questionsIds);

  /**
   * Save the responses of the connected user for the provided questions
   *
   * @param userId                    id of the user who responded
   * @param userResponsesForQuestions questions ids and contents that the connected user entered
   * @return List of the saved responses of the user
   * @throws UserNotFoundException     if no user was found
   * @throws QuestionNotFoundException if no question was found
   * @throws IllegalArgumentException if any given argument is invalid
   */
  List<UserResponseForQuestionDto> saveResponsesOfUserForQuestions(Long userId,
                                                                   List<UserResponseForQuestionDto> userResponsesForQuestions);
}
