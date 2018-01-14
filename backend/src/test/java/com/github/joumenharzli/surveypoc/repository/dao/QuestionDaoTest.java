package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.util.TestTimeWatcher;

/**
 * QuestionDaoTest
 *
 * @author Joumen HARZLI
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionDaoTest {

  @Rule
  public TestRule watcher = new TestTimeWatcher();
  @Autowired
  private QuestionDao questionDao;

  @Test
  public void findAllSubjectsAndQuestions() throws Exception {
    List<Question> questions = questionDao.findAllQuestionsAndSubjects();
    Assert.assertNotNull(questions);
    Assert.assertEquals(questions.size(), 3);
  }

}
