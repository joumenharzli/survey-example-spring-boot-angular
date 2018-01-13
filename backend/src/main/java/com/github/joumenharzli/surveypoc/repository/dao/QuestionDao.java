package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.exception.DaoException;

/**
 * Question dao
 *
 * @author Joumen HARZLI
 */
public interface QuestionDao {

  /**
   * find all questions with their subjects
   *
   * @return a list of the questions with subjects
   * @throws DaoException if there is an sql exception
   */
  List<Question> findAllQuestionsAndSubjects();
}
