package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.User;
import com.github.joumenharzli.surveypoc.domain.UserResponse;

/**
 * UserResponseDaoTest
 *
 * @author Joumen HARZLI
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserResponseDaoTest {

  @Autowired
  UserResponseDao userResponseDao;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Before
  public void init() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_responses");
  }

  @Test
  public void addUserResponsesTest() throws Exception {

    Long userId = 1L;
    Long question1Id = 3L;
    Long question2Id = 4L;
    String responseContent = RandomStringUtils.randomAlphabetic(5);

    int[] result = addUserResponses(userId, question1Id, question2Id, responseContent, responseContent);
    Arrays.stream(result).forEach((updatedRow) -> Assert.assertEquals(updatedRow, 1));

    List<UserResponse> userResponses = findResponsesOfUserForQuestions(userId, question1Id, question2Id);
    userResponses.forEach((userResponse -> Assert.assertEquals(userResponse.getContent(), responseContent)));
  }

  @Test
  public void updateUserResponsesTest() {

    Long userId = 1L;
    Long question1Id = 3L;
    Long question2Id = 4L;

    String nonUpdatedContent = RandomStringUtils.randomAlphabetic(5);
    String updatedContent = RandomStringUtils.randomAlphabetic(5);

    addUserResponses(userId, question1Id, question2Id, nonUpdatedContent, nonUpdatedContent);

    List<UserResponse> userResponses = findResponsesOfUserForQuestions(userId, question1Id, question2Id);
    userResponses.forEach((userResponse -> userResponse.setContent(updatedContent)));

    int[] result = userResponseDao.updateUserResponses(userResponses);
    Arrays.stream(result).forEach((updatedRow) -> Assert.assertEquals(updatedRow, 1));

    List<UserResponse> updatedUserResponses = findResponsesOfUserForQuestions(userId, question1Id, question2Id);
    updatedUserResponses.forEach((userResponse -> Assert.assertEquals(userResponse.getContent(), updatedContent)));

  }

  private int[] addUserResponses(Long userId, Long question1Id, Long question2Id,
                                 String response1Content, String response2Content) {
    UserResponse userResponse1 = createUserResponse(userId, question1Id, response1Content);
    UserResponse userResponse2 = createUserResponse(userId, question2Id, response2Content);

    return userResponseDao.addUserResponses(Arrays.asList(userResponse1, userResponse2));
  }

  private List<UserResponse> findResponsesOfUserForQuestions(Long userId, Long question1Id, Long question2Id) {
    return userResponseDao.findResponsesOfUserByUserIdAndQuestionIds(userId,
        Arrays.asList(question1Id, question2Id));
  }

  private UserResponse createUserResponse(Long userId, Long questionId, String responseContent) {
    return new UserResponse()
        .user(createUser(userId))
        .question(createQuestion(questionId))
        .content(responseContent);
  }

  private Question createQuestion(Long questionId) {
    return new Question()
        .id(questionId);
  }

  private User createUser(Long userId) {
    return new User()
        .id(userId);
  }

}
