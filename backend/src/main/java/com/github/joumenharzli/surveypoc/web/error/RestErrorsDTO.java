package com.github.joumenharzli.surveypoc.web.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * A representation for the rest error
 *
 * @author Joumen HARZLI
 */
public class RestErrorsDTO implements Serializable {

  private List<RestErrorDTO> errors;

  /**
   * Constructor for the rest errors entity
   */
  public RestErrorsDTO() {
    errors = new ArrayList<>();
  }

  /**
   * Constructor for the rest errors entity
   *
   * @param error first error to add to the list of the errors use {@code addError}
   *              to add other errors
   */
  public RestErrorsDTO(RestErrorDTO error) {
    this.addError(error);
  }

  /**
   * Add a rest error to the list of errors
   *
   * @param error error to add to the list
   */
  public void addError(RestErrorDTO error) {
    Assert.notNull(error, "Cannot add a null error to the list of rest errors");

    if (errors == null) {
      errors = new ArrayList<>();
    }

    errors.add(error);
  }

  /**
   * Get the list of the errors
   *
   * @return list of errors
   */
  public List<RestErrorDTO> getErrors() {
    return errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RestErrorsDTO that = (RestErrorsDTO) o;

    return new EqualsBuilder()
        .append(errors, that.errors)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(errors)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("errors", errors)
        .toString();
  }
}
