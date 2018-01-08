package com.github.joumenharzli.surveypoc.repository;

import java.sql.SQLException;
import java.util.List;

import com.github.joumenharzli.surveypoc.domain.Question;

/**
 * Subject Dao
 *
 * @author Joumen HARZLI
 */
public interface QuestionDao {

  /**
   * find all the subjects and their questions
   *
   * @return a list of the questions with subjects
   */
  List<Question> findAllSubjectsAndQuestions() throws SQLException;
}
