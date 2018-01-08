package com.github.joumenharzli.surveypoc.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.joumenharzli.surveypoc.domain.Question;

/**
 * QuestionDaoTest
 *
 * @author Joumen HARZLI
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionDaoTest {

  @Autowired
  private QuestionDao questionDao;

  @Test
  public void findAllSubjectsAndQuestions() throws Exception {
    List<Question> subjects = questionDao.findAllSubjectsAndQuestions();
    Assert.assertNotNull(subjects);
    Assert.assertEquals(subjects.size(), 3);
  }

}
