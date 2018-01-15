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
