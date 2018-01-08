package com.github.joumenharzli.surveypoc.service.mapper;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.Question;
import com.github.joumenharzli.surveypoc.domain.Subject;
import com.github.joumenharzli.surveypoc.service.dto.SubjectDto;

/**
 * SubjectMapper
 *
 * @author Joumen HARZLI
 */
@Mapper(componentModel = "spring")
@DecoratedWith(SubjectMapperDecorator.class)
@Service
public interface SubjectMapper {

  List<SubjectDto> questionsToSubjectsDto(List<Question> questions);

  SubjectDto toDto(Subject subject);
}
