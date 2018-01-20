/*
 * Copyright (C) 2018 Joumen Harzli
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.github.joumenharzli.surveypoc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User Response entity
 *
 * @author Joumen Harzli
 */
public class UserResponse {

  private String content;
  private Question question;
  private User user;

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

    UserResponse that = (UserResponse) o;

    return new EqualsBuilder()
        .append(question, that.question)
        .append(user, that.user)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(question)
        .append(user)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("content", content)
        .append("question", question)
        .append("user", user)
        .toString();
  }
}
