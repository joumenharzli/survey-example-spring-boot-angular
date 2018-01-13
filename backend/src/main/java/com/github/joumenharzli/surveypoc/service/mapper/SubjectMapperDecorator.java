package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;

/**
 * SubjectMapperDecorator
 *
 * @author Joumen HARZLI
 */
public abstract class SubjectMapperDecorator implements SubjectMapper {

  @Autowired
  @Qualifier("delegate")
  private SubjectMapper delegate;

  @Autowired
  private QuestionMapper questionMapper;

  @Override
  public List<SubjectDto> questionsToSubjectsDto(List<Question> questions) {
    Assert.notNull(questions, "Cannot map a null list of questions to a list of subject dtos");

    //@formatter:off
    return questions
        .stream()
        /* create a of Map<SubjectDto,List<QuestionDto>> */
        .collect(Collectors.groupingBy(question -> delegate.toDto(question.getSubject()),
                                                   LinkedHashMap::new,
                                                   Collectors.mapping(
                                                       question -> questionMapper.toDto(question), Collectors.toList())))
        .entrySet()
        .stream()
        /* move the questions list in the map to the list in the subject */
        .map(e -> {
          e.getKey().addQuestions(e.getValue());
          return e.getKey();
        })
        .collect(Collectors.toList());
    //@formatter:on
  }

}
