package com.github.joumenharzli.surveypoc.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.repository.dao.QuestionDao;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;
import com.github.joumenharzli.surveypoc.service.mapper.SubjectMapper;

/**
 * Subject service default implementation
 *
 * @author Joumen HARZLI
 */
@Service
public class SimpleSubjectService implements SubjectService {

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
    List<Question> questions = questionDao.findAllQuestionsAndSubjects();

    if (questions != null) {
      return subjectMapper.questionsToSubjectsDto(questions);
    }

    return Collections.emptyList();
  }

}
