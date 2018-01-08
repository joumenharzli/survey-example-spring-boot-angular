package com.github.joumenharzli.surveypoc.service.dto;

/**
 * QuestionDto
 *
 * @author Joumen HARZLI
 */
public class QuestionDto {
  private Long id;
  private String label;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    QuestionDto that = (QuestionDto) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "QuestionDto{" +
        "id=" + id +
        ", label='" + label + '\'' +
        '}';
  }
}
