package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

  private static final String FIND_QUESTIONS_BY_IDS = "SELECT q.id AS id FROM questions q WHERE id IN (:questions_ids)";

  private final NamedParameterJdbcTemplate parameterJdbcTemplate;
  private final JdbcTemplate jdbcTemplate;

  private final ResultSetExtractor<List<Question>> selectQuestionAndSubjectResultSetExtractor =
      JdbcTemplateMapperFactory
          .newInstance()
          .addKeys("id", "subject_id")
          .newResultSetExtractor(Question.class);

  public JdbcQuestionDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate parameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.parameterJdbcTemplate = parameterJdbcTemplate;
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

  /**
   * Returns the list of ids of the not found questions using ids
   *
   * @param questionsId ids of the questions to check
   * @return a list of the ids of the not found questions
   * @throws DaoException if there is an sql exception
   */
  @Override
  public List<Long> findNonSavedQuestionsByQuestionsIds(List<Long> questionsId) {

    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("questions_ids", questionsId);

    try {
      List<Long> foundQuestionsId = parameterJdbcTemplate.query(FIND_QUESTIONS_BY_IDS, parameters,
          (rs, rowNum) -> rs.getLong(1));

      //@formatter:off
      return questionsId.stream()
                        .filter(id -> !foundQuestionsId.contains(id))
                        .collect(Collectors.toList());
      //@formatter:on
    } catch (Exception exception) {
      throw new DaoException("Unable to find questions by ids", exception);
    }

  }

}
