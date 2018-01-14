package com.github.joumenharzli.surveypoc.domain;

/**
 * UserResponse entity
 *
 * @author Joumen HARZLI
 */
public class UserResponse {

  private Long id;
  private String content;
  private Question question;
  private User user;

  public UserResponse id(Long id) {
    this.id = id;
    return this;
  }

  public UserResponse content(String content) {
    this.content = content;
    return this;
  }

  public UserResponse user(User user) {
    this.user = user;
    return this;
  }

  public UserResponse question(Question question) {
    this.question = question;
    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public Long getQuestionId() {
    if (this.question == null) {
      return null;
    }
    return question.getId();
  }

  public Long getUserId() {
    if (this.user == null) {
      return null;
    }
    return user.getId();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserResponse userResponse = (UserResponse) o;

    return id != null ? id.equals(userResponse.id) : userResponse.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "UserResponse{" +
        "id=" + id +
        ", content='" + content + '\'' +
        ", question='" + question + '\'' +
        ", user='" + user + '\'' +
        '}';
  }

}
