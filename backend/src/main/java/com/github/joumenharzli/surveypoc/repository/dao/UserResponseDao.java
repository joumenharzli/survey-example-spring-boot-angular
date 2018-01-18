package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;

import com.github.joumenharzli.surveypoc.domain.UserResponse;

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
   * @param userId       user who responded
   * @param questionsIds questions that the user may responded
   * @return list of responses
   * @throws DaoException             if there is an sql exception
   * @throws IllegalArgumentException if any given argument is invalid
   */
  List<UserResponse> findResponsesOfUserByUserIdAndQuestionIds(Long userId, List<Long> questionsIds);
}
