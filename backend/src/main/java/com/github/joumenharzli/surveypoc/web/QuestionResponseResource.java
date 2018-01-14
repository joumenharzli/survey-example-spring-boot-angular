package com.github.joumenharzli.surveypoc.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.UserResponse;
import com.github.joumenharzli.surveypoc.service.UserResponseService;
import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;

/**
 * Rest Resource for the entity {@link UserResponse} and {@link Question}
 *
 * @author Joumen HARZLI
 */
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionResponseResource {

  /* because this example is not using Spring Security yet
   * the user id is simply hardcoded */
  private static final Long USER_ID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(QuestionResponseResource.class);

  private final UserResponseService userResponseService;

  public QuestionResponseResource(UserResponseService userResponseService) {
    this.userResponseService = userResponseService;
  }

  /**
   * POST  /responses : Get the responses of the connected user for the provided questions
   * the method {@code POST} was used because of the limitation in the http url max length
   *
   * @param questionsId ids of the questions that the user may responded
   * @return the ResponseEntity with status 200 (OK) and list of responses of the user
   * and the ResponseEntity with status 500 if the request body is invalid
   */
  @PostMapping("/responses")
  List<UserResponseForQuestionDto> getResponsesOfConnectUserForQuestions(@RequestBody List<Long> questionsId) {
    LOGGER.debug("REST request to get the responses of the connected user for the questions with ids {}", questionsId);
    return userResponseService.findResponsesOfUserForQuestions(USER_ID, questionsId);
  }

}
