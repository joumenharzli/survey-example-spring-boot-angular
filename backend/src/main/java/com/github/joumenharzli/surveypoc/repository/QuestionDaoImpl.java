package com.github.joumenharzli.surveypoc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.Subject;

/**
 * QuestionDaoImpl
 *
 * @author Joumen HARZLI
 */
@Repository
public class QuestionDaoImpl implements QuestionDao {

  private static final String SELECT_SUBJECTS_QUESTIONS = "SELECT sub.id subId, sub.label subLabel," +
      " qs.id qsId, qs.label qsLabel FROM subjects sub " +
      "INNER JOIN questions qs ON qs.subject_id LIKE sub.id";

  private final JdbcTemplate jdbcTemplate;

  public QuestionDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * find all the subjects and their questions
   *
   * @return a list of the questions with subjects
   */
  @Override
  public List<Question> findAllSubjectsAndQuestions() throws SQLException {
    return jdbcTemplate.query(SELECT_SUBJECTS_QUESTIONS, (rs, rowNum) -> toQuestionWithSubject(rs));
  }

  private Question toQuestionWithSubject(ResultSet resultSet) throws SQLException {
    return new Question()
        .id(resultSet.getLong("qsId"))
        .label(resultSet.getString("qsLabel"))
        .subject(toSubject(resultSet));
  }

  private Subject toSubject(ResultSet resultSet) throws SQLException {
    return new Subject()
        .id(resultSet.getLong("subId"))
        .label(resultSet.getString("subLabel"));
  }
}
