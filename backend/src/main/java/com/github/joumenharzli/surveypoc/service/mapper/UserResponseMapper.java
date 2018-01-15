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
 * UserResponseMapper
 *
 * @author Joumen HARZLI
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
