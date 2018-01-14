package com.github.joumenharzli.surveypoc.util;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test rule that calculates how the test took time to finish
 *
 * @author Joumen HARZLI
 */
public class TestTimeWatcher extends TestWatcher {

  private final static Logger LOGGER = LoggerFactory.getLogger(TestTimeWatcher.class);

  private long start;

  protected void starting(Description description) {
    LOGGER.info("=> STARTING TEST: " + description.getMethodName());
    start = System.currentTimeMillis();
  }

  @Override
  protected void finished(Description description) {
    long end = System.currentTimeMillis();
    LOGGER.info("=> TEST " + description.getMethodName() + " TOOK " + (end - start) + "ms");
  }

}
