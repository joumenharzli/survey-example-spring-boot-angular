package com.github.joumenharzli.surveypoc.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.joumenharzli.surveypoc.service.SubjectService;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;

/**
 * Rest resource for subject entity
 *
 * @author Joumen HARZLI
 */
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectResource {

  private final SubjectService subjectService;

  public SubjectResource(SubjectService subjectService) {
    this.subjectService = subjectService;
  }

  /**
   * GET  /subjects : get all the find all the subjects and their questions.
   *
   * @return the ResponseEntity with status 200 (OK) and the list the subjects and their questions
   */
  @GetMapping
  public List<SubjectDto> findAllSubjectsAndQuestions() {
    return subjectService.findAllSubjectsAndQuestions();
  }

}
