package com.github.joumenharzli.surveypoc.repository.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

  @Test
  public void addUserResponsesTest() throws Exception {

    Long userId = 1L;
    Long question1Id = 3L;
    Long question2Id = 4L;
    String response1Content = RandomStringUtils.randomAlphabetic(5);
    String response2Content = RandomStringUtils.randomAlphabetic(5);

    addUserResponses(userId, question1Id, question2Id, response1Content, response2Content);

    List<UserResponse> userResponses = findResponsesOfUserForQuestions(userId, question1Id, question2Id);
    Assert.assertEquals(userResponses.size(), 2);
    Assert.assertNotNull(userResponses.get(0));
    Assert.assertNotNull(userResponses.get(1));
  }

  private void addUserResponses(Long userId, Long question1Id, Long question2Id,
                                String response1Content, String response2Content) {
    UserResponse userResponse1 = createUserResponse(userId, question1Id, response1Content);
    UserResponse userResponse2 = createUserResponse(userId, question2Id, response2Content);

    userResponseDao.addUserResponses(Arrays.asList(userResponse1, userResponse2));
  }

  private List<UserResponse> findResponsesOfUserForQuestions(Long userId, Long question1Id, Long question2Id) {
    return userResponseDao.findResponsesOfUserForQuestions(createUser(userId),
        Arrays.asList(createQuestion(question1Id), createQuestion(question2Id)));
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
