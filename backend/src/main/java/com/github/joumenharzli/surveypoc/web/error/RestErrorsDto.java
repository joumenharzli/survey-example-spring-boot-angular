/*
 * Copyright (C) 2018 Joumen Harzli
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

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
public class RestErrorsDto implements Serializable {

  private List<RestErrorDto> errors;

  /**
   * Constructor for the rest errors entity
   */
  public RestErrorsDto() {
    errors = new ArrayList<>();
  }

  /**
   * Constructor for the rest errors entity
   *
   * @param error first error to add to the list of the errors use {@code addError}
   *              to add other errors
   */
  public RestErrorsDto(RestErrorDto error) {
    this.addError(error);
  }

  /**
   * Add a rest error to the list of errors
   *
   * @param error error to add to the list
   */
  public void addError(RestErrorDto error) {
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
  public List<RestErrorDto> getErrors() {
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

    RestErrorsDto that = (RestErrorsDto) o;

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
