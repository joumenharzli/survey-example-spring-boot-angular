package com.github.joumenharzli.surveypoc.service.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * SubjectDto
 *
 * @author Joumen HARZLI
 */
public class SubjectDto {
  private Long id;
  private String label;
  private List<QuestionDto> questions;

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

  public void addQuestions(List<QuestionDto> questions) {
    Assert.notNull(questions, String.format("Cannot add null questions to subject %s", this.toString()));
    if (!questions.isEmpty()) {
      questions.forEach(this::addQuestion);
    }
  }

  public void addQuestion(QuestionDto question) {
    Assert.notNull(questions, String.format("Cannot add null question to the list of questions in the subject %s",
        this.toString()));

    if (questions == null) {
      questions = new ArrayList<>();
    }
    questions.add(question);
  }

  public List<QuestionDto> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuestionDto> questions) {
    this.questions = questions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SubjectDto that = (SubjectDto) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SubjectDto{" +
        "id=" + id +
        ", label='" + label + '\'' +
        ", questions=" + questions +
        '}';
  }
}
