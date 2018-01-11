package com.github.joumenharzli.surveypoc.repository;

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
   * find all the subjects and their questions
   *
   * @return a list of the questions with subjects
   * @throws DaoException if there is an sql exception
   */
  List<Question> findAllSubjectsAndQuestions();
}
