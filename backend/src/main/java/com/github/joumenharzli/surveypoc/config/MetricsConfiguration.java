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

package com.github.joumenharzli.surveypoc.config;

import java.lang.management.ManagementFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.JvmAttributeGaugeSet;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.*;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuration of Dropwizard metrics
 * Those configurations are accessible via {@code /metrics} endpoint
 * or via the JMX
 *
 * @author Joumen Harzli
 */
@EnableMetrics(proxyTargetClass = true)
@Configuration
public class MetricsConfiguration extends MetricsConfigurerAdapter {

  private static final String PROP_METRIC_REG_JVM_MEMORY = "jvm.memory";
  private static final String PROP_METRIC_REG_JVM_GARBAGE = "jvm.garbage";
  private static final String PROP_METRIC_REG_JVM_THREADS = "jvm.threads";
  private static final String PROP_METRIC_REG_JVM_FILES = "jvm.files";
  private static final String PROP_METRIC_REG_JVM_BUFFERS = "jvm.buffers";
  private static final String PROP_METRIC_REG_JVM_ATTRIBUTE_SET = "jvm.attributes";

  private final HikariDataSource hikariDataSource;

  public MetricsConfiguration(HikariDataSource hikariDataSource) {
    this.hikariDataSource = hikariDataSource;
  }

  @Bean
  @Override
  public MetricRegistry getMetricRegistry() {
    MetricRegistry metricRegistry = new MetricRegistry();

    registerJVMMetrics(metricRegistry);
    registerDatasourceMetrics(metricRegistry);
    reportMetricsViaJMX(metricRegistry);

    return metricRegistry;
  }

  @Bean
  @Override
  public HealthCheckRegistry getHealthCheckRegistry() {
    return new HealthCheckRegistry();
  }

  private void registerJVMMetrics(MetricRegistry metricRegistry) {
    metricRegistry.register(PROP_METRIC_REG_JVM_MEMORY, new MemoryUsageGaugeSet());
    metricRegistry.register(PROP_METRIC_REG_JVM_GARBAGE, new GarbageCollectorMetricSet());
    metricRegistry.register(PROP_METRIC_REG_JVM_THREADS, new ThreadStatesGaugeSet());
    metricRegistry.register(PROP_METRIC_REG_JVM_FILES, new FileDescriptorRatioGauge());
    metricRegistry.register(PROP_METRIC_REG_JVM_BUFFERS, new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
    metricRegistry.register(PROP_METRIC_REG_JVM_ATTRIBUTE_SET, new JvmAttributeGaugeSet());
  }

  private void registerDatasourceMetrics(MetricRegistry metricRegistry) {
    hikariDataSource.setMetricRegistry(metricRegistry);
  }

  private void reportMetricsViaJMX(MetricRegistry metricRegistry) {
    JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
    jmxReporter.start();
  }

}
