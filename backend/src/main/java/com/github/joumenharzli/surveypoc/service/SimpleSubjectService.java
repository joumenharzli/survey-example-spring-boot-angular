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

package com.github.joumenharzli.surveypoc.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.repository.dao.QuestionDao;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;
import com.github.joumenharzli.surveypoc.service.mapper.SubjectMapper;

/**
 * A simple implementation for {@link SubjectService}
 *
 * @author Joumen HARZLI
 */
@Service
public class SimpleSubjectService implements SubjectService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSubjectService.class);

  private final QuestionDao questionDao;
  private final SubjectMapper subjectMapper;

  public SimpleSubjectService(QuestionDao questionDao, SubjectMapper subjectMapper) {
    this.questionDao = questionDao;
    this.subjectMapper = subjectMapper;
  }

  /**
   * find all the subjects and their questions
   *
   * @return a list of the questions with subjects
   */
  @Override
  public List<SubjectDto> findAllSubjectsAndQuestions() {
    LOGGER.debug("Request to get all the subjects and the questions");

    List<Question> questions = questionDao.findAllQuestionsAndSubjects();

    if (questions != null) {
      return subjectMapper.questionsToSubjectsDto(questions);
    }

    return Collections.emptyList();
  }

}
