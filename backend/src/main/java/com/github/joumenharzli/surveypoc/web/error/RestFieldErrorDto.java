package com.github.joumenharzli.surveypoc.web.error;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A representation for the rest field error
 *
 * @author Joumen HARZLI
 */
public class RestFieldErrorDto implements Serializable {

  private String field;
  private String code;
  private String message;

  public RestFieldErrorDto(String field, String code, String message) {
    this.field = field;
    this.code = code;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RestFieldErrorDto that = (RestFieldErrorDto) o;

    return new EqualsBuilder()
        .append(field, that.field)
        .append(code, that.code)
        .append(message, that.message)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(field)
        .append(code)
        .append(message)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("field", field)
        .append("code", code)
        .append("message", message)
        .toString();
  }
}
