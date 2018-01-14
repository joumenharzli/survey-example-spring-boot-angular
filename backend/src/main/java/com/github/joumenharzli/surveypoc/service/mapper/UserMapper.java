package com.github.joumenharzli.surveypoc.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import com.github.joumenharzli.surveypoc.domain.User;

/**
 * UserMapper
 *
 * @author Joumen HARZLI
 */
@Mapper(componentModel = "spring")
@Service
public interface UserMapper {
  User toEntityFromId(Long id);
}
