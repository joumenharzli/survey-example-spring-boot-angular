package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.github.joumenharzli.surveypoc.domain.Question;

/**
 * JDBC implementation for {@link QuestionDao}
 *
 * @author Joumen HARZLI
 */
@Repository
public class JdbcQuestionDao implements QuestionDao {

  private static final String SELECT_QUESTIONS_AND_SUBJECTS = "SELECT q.id AS id, q.label AS label, s.id AS subject_id, " +
      " s.label AS subject_label FROM questions q" +
      " LEFT OUTER JOIN subjects s ON q.subject_id = s.id" +
      " ORDER BY id";

  private final ResultSetExtractor<List<Question>> selectQuestionAndSubjectResultSetExtractor =
      JdbcTemplateMapperFactory
          .newInstance()
          .addKeys("id", "subject_id")
          .newResultSetExtractor(Question.class);

  private final JdbcTemplate jdbcTemplate;

  public JdbcQuestionDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * find all questions with their subjects
   *
   * @return a list of the questions with subjects
   * @throws DaoException if there is an sql exception
   */
  @Override
  public List<Question> findAllQuestionsAndSubjects() {
    try {
      return jdbcTemplate.query(SELECT_QUESTIONS_AND_SUBJECTS, selectQuestionAndSubjectResultSetExtractor);
    } catch (Exception exception) {
      throw new DaoException("Unable to find subjects and questions", exception);
    }
  }

}
