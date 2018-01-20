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

package com.github.joumenharzli.surveypoc.web.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

/**
 * Utils for Rest Controllers
 *
 * @author Joumen HARZLI
 */
public final class RestUtils {

  private RestUtils() {
  }

  /**
   * Convert a comma delimited list into a list of {@code Long} values.
   *
   * @param value comma separated text
   * @return list of parsed elements
   */
  public static List<Long> commaDelimitedListToLongList(String value) {
    return StringUtils.commaDelimitedListToSet(value)
        .stream()
        .map(Long::parseLong)
        .collect(Collectors.toList());
  }
}
