package com.github.joumenharzli.surveypoc.web.error;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A representation for the rest error
 *
 * @author Joumen HARZLI
 */
public class RestErrorDTO implements Serializable {
  private String code;
  private String message;
  private Object meta;

  public RestErrorDTO(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public RestErrorDTO(String code, String message, Object meta) {
    this(code, message);
    this.meta = meta;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Object getMeta() {
    return meta;
  }

  public void setMeta(Object meta) {
    this.meta = meta;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RestErrorDTO that = (RestErrorDTO) o;

    return new EqualsBuilder()
        .append(code, that.code)
        .append(message, that.message)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(code)
        .toHashCode();
  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this)
        .append("code", code)
        .append("message", message);

    if (meta != null) {
      builder.append("meta", meta);
    }

    return builder.toString();
  }
}
