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

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.Subject;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;

/**
 * Mapper for {@link Subject} and {@link SubjectDto}
 *
 * @author Joumen Harzli
 */
@Mapper(componentModel = "spring")
@DecoratedWith(SubjectMapperDecorator.class)
@Service
public interface SubjectMapper {

  List<SubjectDto> questionsToSubjectsDto(List<Question> questions);

  SubjectDto toDto(Subject subject);

}
