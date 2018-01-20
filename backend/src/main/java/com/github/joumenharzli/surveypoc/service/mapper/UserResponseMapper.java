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

package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.User;
import com.github.joumenharzli.surveypoc.domain.UserResponse;
import com.github.joumenharzli.surveypoc.service.dto.UserResponseForQuestionDto;

/**
 * Mapper for {@link UserResponse} and {@link UserResponseForQuestionDto}
 *
 * @author Joumen Harzli
 */
@Mapper(componentModel = "spring")
@Service
public interface UserResponseMapper {

  @Mapping(source = "question.id", target = "questionId")
  UserResponseForQuestionDto userResponseToUserResponseForQuestionDto(UserResponse entity);

  List<UserResponseForQuestionDto> userResponseListToUserResponseForQuestionDtoList(List<UserResponse> entity);


  @Mapping(source = "userResponseForQuestion.questionId", target = "question.id")
  UserResponse userResponseForQuestionDtoToUserResponse(UserResponseForQuestionDto userResponseForQuestion);

  default List<UserResponse> userResponsesForQuestionsDtoToUserResponsesList(List<UserResponseForQuestionDto> userResponseForQuestions,
                                                                             User user) {

    return userResponseForQuestions.stream()
        .map(
            userResponseForQuestionDto -> {
              UserResponse userResponse = userResponseForQuestionDtoToUserResponse(userResponseForQuestionDto);
              userResponse.setUser(user);
              return userResponse;
            })
        .collect(Collectors.toList());
  }

  default List<Long> userResponsesForQuestionsToQuestionsIdsList(List<UserResponseForQuestionDto> userResponsesForQuestions) {
    return userResponsesForQuestions.stream()
        .map(UserResponseForQuestionDto::getQuestionId)
        .collect(Collectors.toList());
  }

}
