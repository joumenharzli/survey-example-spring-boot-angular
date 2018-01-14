package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.service.dto.QuestionDto;

/**
 * QuestionMapper
 *
 * @author Joumen HARZLI
 */
@Mapper(componentModel = "spring")
@Service
public interface QuestionMapper {
  QuestionDto toDto(Question question);

  List<Question> toEntitiesFromIds(List<Long> questionsIds);
}
