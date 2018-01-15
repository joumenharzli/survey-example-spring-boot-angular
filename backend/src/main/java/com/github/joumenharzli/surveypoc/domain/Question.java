package com.github.joumenharzli.surveypoc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Question entity
 *
 * @author Joumen HARZLI
 */
public class Question {

  private Long id;
  private String label;
  private Subject subject;

  public Question id(Long id) {
    this.id = id;
    return this;
  }

  public Question label(String label) {
    this.label = label;
    return this;
  }

  public Question subject(Subject subject) {
    this.subject = subject;
    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Long getSubjectId() {
    if (subject == null) {
      return null;
    }
    return subject.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Question question = (Question) o;

    return new EqualsBuilder()
        .append(id, question.id)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("label", label)
        .append("subject", subject)
        .toString();
  }
}
