package com.github.joumenharzli.surveypoc.domain;

/**
 * Subject entity
 *
 * @author Joumen HARZLI
 */
public class Subject {
  private Long id;
  private String label;

  public Subject id(Long id) {
    this.id = id;
    return this;
  }

  public Subject label(String label) {
    this.label = label;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Subject subject = (Subject) o;

    return id != null ? id.equals(subject.id) : subject.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Subject{" +
        "id=" + id +
        ", label='" + label + '\'' +
        '}';
  }
}
