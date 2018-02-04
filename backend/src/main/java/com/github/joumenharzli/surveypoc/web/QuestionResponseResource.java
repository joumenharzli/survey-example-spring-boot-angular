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

package com.github.joumenharzli.surveypoc.web;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.UserResponse;
import com.github.joumenharzli.surveypoc.service.UserResponseService;
import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;
import com.github.joumenharzli.surveypoc.service.dto.UserResponsesForQuestionListDto;
import com.github.joumenharzli.surveypoc.web.error.RestErrorDto;

import static com.github.joumenharzli.surveypoc.web.util.RestUtils.commaDelimitedListToLongList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest Resource for the entities {@link UserResponse} and {@link Question}
 *
 * @author Joumen Harzli
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
   * GET  /:questionsId/responses/me : Get the responses of the connected user for the provided questions
   *
   * @param questionsId a comma separated ids of the questions that the user may responded
   * @return the ResponseEntity with status 200 (OK) and list of responses of the user
   * and the ResponseEntity with status 500 if the request body is invalid
   */
  @ApiOperation(notes = "Returns all the found responses of the connected user for the provided questions.",
      value = "Get all responses of the connected user for the questions",
      nickname = "getResponsesOfConnectUserForQuestions")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Question or user not found", response = RestErrorDto.class),
  })
  @Timed
  @GetMapping("/{questionsId}/responses/me")
  public List<UserResponseForQuestionDto> getResponsesOfConnectUserForQuestions(
      @ApiParam(value = "A comma separated ids of the questions that the user may responded example: 1, 2, 3",
          required = true)
      @PathVariable("questionsId") String questionsId) {

    LOGGER.debug("REST request to get the responses of the connected user for the questions with ids {}", questionsId);
    return userResponseService.findResponsesOfUserForQuestions(USER_ID, commaDelimitedListToLongList(questionsId));
  }

  /**
   * POST  /responses/me : Save the responses of the connected user for the provided questions
   *
   * @param userResponseForQuestions questions ids and contents that the connected user entered
   * @return the ResponseEntity with status 200 (OK) and list of the saved responses of the user
   * and the ResponseEntity with status 500 if the request body is invalid
   */
  @ApiOperation(notes = "Add and update the responses of the connected user for the provided questions then " +
      "returns all the list of the saved responses of the user.",
      value = "Save the responses of the connected user for the provided questions",
      nickname = "getResponsesOfConnectUserForQuestions")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Question or user not found", response = RestErrorDto.class),
  })
  @Timed
  @PostMapping("/responses/me")
  public List<UserResponseForQuestionDto> saveResponsesOfConnectUserForQuestions(@Valid @RequestBody
                                                                                     UserResponsesForQuestionListDto userResponseForQuestions) {
    LOGGER.debug("REST request to save the responses of the connected user for the questions {}", userResponseForQuestions);
    return userResponseService.saveResponsesOfUserForQuestions(USER_ID, userResponseForQuestions.getResponses());
  }

}
