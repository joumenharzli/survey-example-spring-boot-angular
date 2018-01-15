package com.github.joumenharzli.surveypoc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    return new EqualsBuilder()
        .append(id, subject.id)
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
        .toString();
  }
}
