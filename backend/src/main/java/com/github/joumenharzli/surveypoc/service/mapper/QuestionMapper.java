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

package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.service.dto.QuestionDto;

/**
 * Mapper for {@link Question} and {@link QuestionDto}
 *
 * @author Joumen HARZLI
 */
@Mapper(componentModel = "spring")
@Service
public interface QuestionMapper {

  QuestionDto questionToQuestionDto(Question question);

  @Mapping(source = "questionId", target = "id")
  Question questionIdToQuestion(Long questionId);

  List<Question> questionsIdsListToQuestionList(List<Long> questionsIds);

}
