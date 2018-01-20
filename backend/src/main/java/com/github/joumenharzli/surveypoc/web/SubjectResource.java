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

package com.github.joumenharzli.surveypoc.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(SubjectResource.class);

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
    LOGGER.debug("REST request to get all the subjects and the questions");

    return subjectService.findAllSubjectsAndQuestions();
  }

}
