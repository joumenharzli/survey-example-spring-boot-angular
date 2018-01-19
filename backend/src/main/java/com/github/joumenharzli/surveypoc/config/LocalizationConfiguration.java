package com.github.joumenharzli.surveypoc.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * Localization Configuration
 *
 * @author Joumen HARZLI
 */
@Configuration
public class LocalizationConfiguration {


  /**
   * Use {@code Accept-language} header of the HTTP request to set the client local
   * <p>
   * If not specified {@code Locale.US} will be used
   *
   * @return instance of {@link LocaleResolver}
   */
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }

  /**
   * Set the source of i18n messages under "resources/i18n/messages"
   *
   * @return instance of {@link ResourceBundleMessageSource}
   */
  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/messages");
    source.setUseCodeAsDefaultMessage(true);
    return source;
  }

}
