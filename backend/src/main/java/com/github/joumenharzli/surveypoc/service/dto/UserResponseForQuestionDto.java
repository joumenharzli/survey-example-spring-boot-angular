package com.github.joumenharzli.surveypoc.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User Response For Question Dto
 *
 * @author Joumen HARZLI
 */
public class UserResponseForQuestionDto {

  private Long questionId;
  private String content;

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserResponseForQuestionDto that = (UserResponseForQuestionDto) o;

    return new EqualsBuilder()
        .append(questionId, that.questionId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(questionId)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("questionId", questionId)
        .append("content", content)
        .toString();
  }
}
