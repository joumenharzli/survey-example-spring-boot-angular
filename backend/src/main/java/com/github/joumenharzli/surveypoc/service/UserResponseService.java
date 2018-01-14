package com.github.joumenharzli.surveypoc.service;

import java.util.List;

import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;

/**
 * User Response Service
 *
 * @author Joumen HARZLI
 */
public interface UserResponseService {

  /**
   * Find the responses for the provided questions and user
   *
   * @param userId      id of the user who responded
   * @param questionsId ids of the questions that the user may responded
   * @return list of responses of the user
   * @throws IllegalArgumentException if any given argument is invalid
   */
  List<UserResponseForQuestionDto> findResponsesOfUserForQuestions(Long userId, List<Long> questionsId);
}
