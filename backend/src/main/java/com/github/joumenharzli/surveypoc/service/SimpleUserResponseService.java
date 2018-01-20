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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.github.joumenharzli.surveypoc.domain.User;
import com.github.joumenharzli.surveypoc.domain.UserResponse;
import com.github.joumenharzli.surveypoc.exception.QuestionNotFoundException;
import com.github.joumenharzli.surveypoc.exception.UserNotFoundException;
import com.github.joumenharzli.surveypoc.repository.dao.QuestionDao;
import com.github.joumenharzli.surveypoc.repository.dao.UserDao;
import com.github.joumenharzli.surveypoc.repository.dao.UserResponseDao;
import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;
import com.github.joumenharzli.surveypoc.service.mapper.QuestionMapper;
import com.github.joumenharzli.surveypoc.service.mapper.UserMapper;
import com.github.joumenharzli.surveypoc.service.mapper.UserResponseMapper;

/**
 * A simple implementation for {@link UserResponseService}
 *
 * @author Joumen HARZLI
 */
@Service
public class SimpleUserResponseService implements UserResponseService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUserResponseService.class);

  private final UserResponseDao userResponseDao;
  private final UserMapper userMapper;
  private final QuestionMapper questionMapper;
  private final UserResponseMapper userResponseMapper;
  private final QuestionDao questionDao;
  private final UserDao userDao;

  public SimpleUserResponseService(UserResponseDao userResponseDao, UserMapper userMapper,
                                   QuestionMapper questionMapper, UserResponseMapper userResponseMapper,
                                   QuestionDao questionDao, UserDao userDao) {
    this.userResponseDao = userResponseDao;
    this.userMapper = userMapper;
    this.questionMapper = questionMapper;
    this.userResponseMapper = userResponseMapper;
    this.questionDao = questionDao;
    this.userDao = userDao;
  }

  /**
   * Find the responses for the provided questions and user
   *
   * @param userId       id of the user who responded
   * @param questionsIds ids of the questions that the user may responded
   * @return list of responses of the user
   * @throws UserNotFoundException     if no user was found
   * @throws QuestionNotFoundException if no question was found
   * @throws IllegalArgumentException  if any given argument is invalid
   */
  @Override
  public List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(Long userId, List<Long> questionsIds) {
    LOGGER.debug("Request to get the responses of the user {} for the questions with ids {}", userId, questionsIds);

    Assert.notNull(userId, "Id of the user cannot be null");
    Assert.notEmpty(questionsIds, "Ids of the questions cannot be null or empty");
    questionsIds.forEach(questionId -> Assert.notNull(questionId, "Id of the question cannot be null"));

    verifyUserAndQuestionsExists(userId, questionsIds);

    return findResponsesOfUserByUserIdAndQuestionIds(userId, questionsIds);
  }

  /**
   * Save the responses of the connected user for the provided questions
   *
   * @param userId                    id of the user who responded
   * @param userResponsesForQuestions questions ids and contents that the connected user entered
   * @return List of the saved responses of the user
   * @throws UserNotFoundException     if no user was found
   * @throws QuestionNotFoundException if no question was found
   * @throws IllegalArgumentException  if any given argument is invalid
   */
  @Override
  public List<UserResponseForQuestionDto> saveResponsesOfUserForQuestions(Long userId,
                                                                          List<UserResponseForQuestionDto> userResponsesForQuestions) {
    LOGGER.debug("Request to save the responses of the user {} for the questions {}", userId, userResponsesForQuestions);

    Assert.notNull(userId, "Id of the user cannot be null");
    Assert.notEmpty(userResponsesForQuestions, "User responses for the questions cannot be null or empty");

    List<Long> questionsIds = userResponseMapper.userResponsesForQuestionsToQuestionsIdsList(userResponsesForQuestions);

    verifyUserAndQuestionsExists(userId, questionsIds);

    User user = this.userMapper.toEntityFromId(userId);

    //@formatter:off
    List<UserResponse> userResponses = userResponseMapper
                                      .userResponsesForQuestionsDtoToUserResponsesList(userResponsesForQuestions, user);
    //@formatter:on

    List<UserResponse> existingUserResponses = userResponseDao
        .findResponsesOfUserByUserIdAndQuestionIds(userId, questionsIds);

    saveResponsesOfUserForQuestions(userResponses, existingUserResponses);

    return findResponsesOfUserByUserIdAndQuestionIds(userId, questionsIds);
  }

  /**
   * Update the existing user responses and save the new ones extracted from the provided user reponses
   *
   * @param userResponses         the provided user responses
   * @param existingUserResponses the existing user responses
   */
  private void saveResponsesOfUserForQuestions(List<UserResponse> userResponses, List<UserResponse> existingUserResponses) {

    List<UserResponse> userResponsesToAdd = new ArrayList<>();
    List<UserResponse> userResponsesToUpdate = new ArrayList<>();

    userResponses.forEach((userResponse -> {
      if (existingUserResponses.contains(userResponse)) {
        userResponsesToUpdate.add(userResponse);
      } else {
        userResponsesToAdd.add(userResponse);
      }
    }));

    userResponseDao.addUserResponses(userResponsesToAdd);
    userResponseDao.updateUserResponses(userResponsesToUpdate);

  }

  /**
   * Retrieve the response of the provided user for the questions from the database and map results to a list
   * of DTO
   *
   * @param userId       user who responded to the question
   * @param questionsIds ids of the questions
   * @return list of dto of the response of the user
   */
  private List<UserResponseForQuestionDto> findResponsesOfUserByUserIdAndQuestionIds(Long userId, List<Long> questionsIds) {
    List<UserResponse> responses = userResponseDao.findResponsesOfUserByUserIdAndQuestionIds(userId, questionsIds);
    return userResponseMapper.userResponseListToUserResponseForQuestionDtoList(responses);
  }

  /**
   * Verify that the user and the questions exist in the database
   *
   * @param userId       id of the user
   * @param questionsIds ids of the questions
   * @throws UserNotFoundException     if no user was found
   * @throws QuestionNotFoundException if no question was found
   */
  private void verifyUserAndQuestionsExists(Long userId, List<Long> questionsIds) {
    verifyUserExist(userId);
    verifyQuestionsExist(questionsIds);
  }

  /**
   * Verify that the user exist in the database
   *
   * @param userId id of the user
   * @throws UserNotFoundException if no user was found
   */
  private void verifyUserExist(Long userId) {
    List<Long> nonExistingUsersIds = userDao.findNonExistingUsersByUsersIds(Collections.singletonList(userId));
    if (!CollectionUtils.isEmpty(nonExistingUsersIds)) {
      throw new UserNotFoundException(nonExistingUsersIds);
    }
  }

  /**
   * Verify that the questions exists in the database
   *
   * @param questionsIds ids of the questions
   * @throws QuestionNotFoundException if no question was found
   */
  private void verifyQuestionsExist(List<Long> questionsIds) {
    List<Long> nonExistingQuestionsIds = questionDao.findNonExistingQuestionsByQuestionsIds(questionsIds);
    if (!CollectionUtils.isEmpty(nonExistingQuestionsIds)) {
      throw new QuestionNotFoundException(nonExistingQuestionsIds);
    }
  }

}
