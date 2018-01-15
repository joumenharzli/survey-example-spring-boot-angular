package com.github.joumenharzli.surveypoc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.joumenharzli.surveypoc.domain.User;
import com.github.joumenharzli.surveypoc.domain.UserResponse;
import com.github.joumenharzli.surveypoc.repository.dao.UserResponseDao;
import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;
import com.github.joumenharzli.surveypoc.service.mapper.QuestionMapper;
import com.github.joumenharzli.surveypoc.service.mapper.UserMapper;
import com.github.joumenharzli.surveypoc.service.mapper.UserResponseMapper;

/**
 * A simple implementation of the {@link UserResponseService}
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

  public SimpleUserResponseService(UserResponseDao userResponseDao, UserMapper userMapper,
                                   QuestionMapper questionMapper, UserResponseMapper userResponseMapper) {
    this.userResponseDao = userResponseDao;
    this.userMapper = userMapper;
    this.questionMapper = questionMapper;
    this.userResponseMapper = userResponseMapper;
  }

  /**
   * Find the responses for the provided questions and user
   *
   * @param userId      id of the user who responded
   * @param questionsId ids of the questions that the user may responded
   * @return list of responses of the user
   * @throws IllegalArgumentException if any given argument is invalid
   */
  @Override
  public List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(Long userId, List<Long> questionsId) {
    LOGGER.debug("Request to get the responses of the user {} for the questions with ids {}", userId, questionsId);

    Assert.notNull(userId, "Id of the user cannot be null");
    Assert.notEmpty(questionsId, "Ids of the questions cannot be null or empty");
    questionsId.forEach(questionId -> Assert.notNull(questionsId, "Id of the question cannot be null"));

    return findResponsesOfUserForQuestions(userMapper.toEntityFromId(userId), questionsId);
  }

  /**
   * Save the responses of the connected user for the provided questions
   *
   * @param userId                    id of the user who responded
   * @param userResponsesForQuestions questions ids and contents that the connected user entered
   * @return List of the saved responses of the user
   * @throws IllegalArgumentException if any given argument is invalid
   */
  @Override
  public List<UserResponseForQuestionDto> saveResponsesOfUserForQuestions(Long userId,
                                                                          List<UserResponseForQuestionDto> userResponsesForQuestions) {
    LOGGER.debug("Request to save the responses of the user {} for the questions {}", userId, userResponsesForQuestions);

    Assert.notNull(userId, "Id of the user cannot be null");
    Assert.notEmpty(userResponsesForQuestions, "User responses for the questions cannot be null or empty");

    User user = userMapper.toEntityFromId(userId);

    userResponseDao.addUserResponses(
        userResponseMapper.userResponsesForQuestionsDtoToUserResponsesList(userResponsesForQuestions, user));

    return findResponsesOfUserForQuestions(user, userResponseMapper.userResponsesForQuestionsToQuestionsIdsList(userResponsesForQuestions));
  }


  private List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(User user, List<Long> questionsId) {
    List<UserResponse> responses = userResponseDao.findResponsesOfUserForQuestions(user,
        questionMapper.questionsIdsListToQuestionList(questionsId));

    return userResponseMapper.userResponseListToUserResponseForQuestionDtoList(responses);
  }

}
