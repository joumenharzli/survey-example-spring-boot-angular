package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;

import com.github.joumenharzli.surveypoc.domain.Question;

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

  /**
   * Returns the list of ids of the not found questions using ids
   *
   * @param questionsId ids of the questions to check
   * @return a list of the ids of the not found questions
   * @throws DaoException if there is an sql exception
   */
  List<Long> findNonSavedQuestionsByQuestionsIds(List<Long> questionsId);
}
