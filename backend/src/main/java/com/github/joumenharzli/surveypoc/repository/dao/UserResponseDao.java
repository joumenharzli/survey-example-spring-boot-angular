package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.User;
import com.github.joumenharzli.surveypoc.domain.UserResponse;
import com.github.joumenharzli.surveypoc.exception.DaoException;

/**
 * User Response Dao
 *
 * @author Joumen HARZLI
 */
public interface UserResponseDao {

  /**
   * Add a new responses of the user
   *
   * @param userResponses entities to save
   * @throws DaoException             if there is an sql exception
   * @throws IllegalArgumentException if any given argument is invalid
   */
  void addUserResponses(List<UserResponse> userResponses);

  /**
   * Find the responses for the provided questions and user
   *
   * @param user      user who responded
   * @param questions questions that the user may responded
   * @return list of responses
   * @throws QuestionNotFoundException if at least one question was not found
   * @throws DaoException              if there is an sql exception
   */
  List<UserResponse> findResponsesOfUserForQuestions(User user, List<Question> questions);
}
