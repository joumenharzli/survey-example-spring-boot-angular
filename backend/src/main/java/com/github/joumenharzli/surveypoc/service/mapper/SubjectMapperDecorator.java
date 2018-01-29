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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.Subject;
import com.github.joumenharzli.surveypoc.service.dto.QuestionDto;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * A decorator for the mapper for {@link Subject} and {@link SubjectDto}
 * for custom methods
 *
 * @author Joumen Harzli
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

    Multimap<Subject, Question> multimap = Multimaps.index(questions, Question::getSubject);

    return multimap.keySet().stream().map(subject -> {

      SubjectDto subjectDto = delegate.toDto(subject);

      List<QuestionDto> questionsDtos = multimap.get(subject).stream()
          .map(question -> questionMapper.questionToQuestionDto(question)).collect(Collectors.toList());

      subjectDto.addQuestions(questionsDtos);
      return subjectDto;

    }).collect(Collectors.toList());
  }

}
