package com.jwt.mapper;

import com.jwt.domain.Users;
import com.test.model.UserDetailsAO;
import org.mapstruct.Mapper;

/*
 * @Created 2/11/24
 * @Project springboot-jwt-microservice
 * @User Kumar Padigeri
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    Users userDtoToEntity(UserDetailsAO user);
}
