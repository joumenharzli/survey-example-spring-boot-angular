package com.github.joumenharzli.surveypoc.service;

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
   * @throws IllegalArgumentException if any given argument is invalid
   */
  @Override
  public List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(Long userId, List<Long> questionsIds) {
    LOGGER.debug("Request to get the responses of the user {} for the questions with ids {}", userId, questionsIds);

    Assert.notNull(userId, "Id of the user cannot be null");
    Assert.notEmpty(questionsIds, "Ids of the questions cannot be null or empty");
    questionsIds.forEach(questionId -> Assert.notNull(questionId, "Id of the question cannot be null"));

    verifyUserAndQuestionsExists(userId, questionsIds);

    return findResponsesOfUserForQuestions(userMapper.toEntityFromId(userId), questionsIds);
  }

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
  @Override
  public List<UserResponseForQuestionDto> saveResponsesOfUserForQuestions(Long userId,
                                                                          List<UserResponseForQuestionDto> userResponsesForQuestions) {
    LOGGER.debug("Request to save the responses of the user {} for the questions {}", userId, userResponsesForQuestions);

    Assert.notNull(userId, "Id of the user cannot be null");
    Assert.notEmpty(userResponsesForQuestions, "User responses for the questions cannot be null or empty");

    List<Long> questionsIds = userResponseMapper.userResponsesForQuestionsToQuestionsIdsList(userResponsesForQuestions);

    verifyUserAndQuestionsExists(userId, questionsIds);

    User user = userMapper.toEntityFromId(userId);

    userResponseDao.addUserResponses(
        userResponseMapper.userResponsesForQuestionsDtoToUserResponsesList(userResponsesForQuestions, user));

    return findResponsesOfUserForQuestions(user, questionsIds);
  }

  /**
   * Retrieve the response of the provided user for the questions from the database and map results to a list
   * of DTO
   *
   * @param user         user who responded to the question
   * @param questionsIds ids of the questions
   * @return list of dto of the response of the user
   */
  private List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(User user, List<Long> questionsIds) {
    List<UserResponse> responses = userResponseDao.findResponsesOfUserForQuestions(user,
        questionMapper.questionsIdsListToQuestionList(questionsIds));

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
    List<Long> nonSavedUsersIds = userDao.findNonSavedUsersByUsersIds(Collections.singletonList(userId));
    if (!CollectionUtils.isEmpty(nonSavedUsersIds)) {
      throw new UserNotFoundException(nonSavedUsersIds);
    }
  }

  /**
   * Verify that the questions exists in the database
   *
   * @param questionsIds ids of the questions
   * @throws QuestionNotFoundException if no question was found
   */
  private void verifyQuestionsExist(List<Long> questionsIds) {
    List<Long> nonSavedQuestionsIds = questionDao.findNonSavedQuestionsByQuestionsIds(questionsIds);
    if (!CollectionUtils.isEmpty(nonSavedQuestionsIds)) {
      throw new QuestionNotFoundException(nonSavedQuestionsIds);
    }
  }

}
