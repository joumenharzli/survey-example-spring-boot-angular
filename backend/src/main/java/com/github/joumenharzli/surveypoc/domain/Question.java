package com.github.joumenharzli.surveypoc.domain;

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

    return id != null ? id.equals(question.id) : question.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Question{" +
        "id=" + id +
        ", label='" + label + '\'' +
        ", subject=" + subject +
        '}';
  }
}
