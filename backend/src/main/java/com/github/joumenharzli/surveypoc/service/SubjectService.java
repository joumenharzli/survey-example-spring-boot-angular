package com.github.joumenharzli.surveypoc.service;

import java.util.List;

import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;

/**
 * Subject service
 *
 * @author Joumen HARZLI
 */
public interface SubjectService {

  /**
   * find all the subjects and their questions
   *
   * @return a list of the questions with subjects
   */
  List<SubjectDto> findAllSubjectsAndQuestions();

}
