package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.Subject;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;

/**
 * SubjectMapperDecoratorTest
 *
 * @author Joumen HARZLI
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectMapperDecoratorTest {

  @Autowired
  SubjectMapper subjectMapper;

  @Test
  public void questionsToSubjectsDto() throws Exception {
    Question q1 = new Question().id(1L).label("question1").subject(new Subject().id(1L));
    Question q2 = new Question().id(2L).label("question2").subject(new Subject().id(1L));
    Question q3 = new Question().id(3L).label("question3").subject(new Subject().id(2L));

    Long start = System.currentTimeMillis();
    List<SubjectDto> subjects = subjectMapper.questionsToSubjectsDto(Arrays.asList(q1, q2, q3));

    Long end = System.currentTimeMillis();
    Assert.assertTrue(end - start < 100L);

    Assert.assertNotNull(subjects);
    Assert.assertEquals(subjects.size(), 2);
    subjects.forEach((this::assertSubjectDtoAndItsQuestionsNotNull));
  }

  private void assertSubjectDtoAndItsQuestionsNotNull(SubjectDto subjectDto) {

    Assert.assertNotNull(subjectDto.getQuestions());
    subjectDto.getQuestions().forEach((questionDto -> {
      Assert.assertNotNull(questionDto.getId());
      Assert.assertNotNull(questionDto.getLabel());
    }));

  }
}
