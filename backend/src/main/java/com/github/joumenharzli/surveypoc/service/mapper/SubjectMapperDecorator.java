package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    List<SubjectDto> subjectsDto = new ArrayList<>();
    questions.forEach(question -> insertSubjectOfQuestionIntoList(question, subjectsDto));
    return subjectsDto;
  }

  private void insertSubjectOfQuestionIntoList(Question question, List<SubjectDto> subjectsDto) {
    Assert.notNull(question, "Cannot map a null question");
    Assert.notNull(question.getSubject(), "Cannot map a null subject in the question " + question);

    SubjectDto subjectDto = delegate.toDto(question.getSubject());
    if (!elementExistsInSubjectsDto(subjectsDto, question)) {
      subjectsDto.add(subjectDto);
    }
    subjectDto.addQuestion(questionMapper.toDto(question));
  }

  private boolean elementExistsInSubjectsDto(List<SubjectDto> subjectsDto, Question question) {
    return subjectsDto.stream().filter((dto) -> Objects.equals(dto.getId(), question.getSubjectId())).count() > 0;
  }

}
