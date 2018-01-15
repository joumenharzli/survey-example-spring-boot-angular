package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.service.dto.QuestionDto;

/**
 * Mapper for {@link Question} and {@link QuestionDto}
 *
 * @author Joumen HARZLI
 */
@Mapper(componentModel = "spring")
@Service
public interface QuestionMapper {

  QuestionDto questionToQuestionDto(Question question);

  @Mapping(source = "questionId", target = "id")
  Question questionIdToQuestion(Long questionId);

  List<Question> questionsIdsListToQuestionList(List<Long> questionsIds);

}
