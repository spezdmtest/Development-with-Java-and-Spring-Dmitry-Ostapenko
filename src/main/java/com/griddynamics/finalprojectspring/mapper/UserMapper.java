package com.griddynamics.finalprojectspring.mapper;

import com.griddynamics.finalprojectspring.dto.UserDTO;
import com.griddynamics.finalprojectspring.entities.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO userDTO);

    @InheritInverseConfiguration
    UserDTO fromUser(User user);

    List<User> toUserList(List<UserDTO> userDTOList);

    List<UserDTO> fromUserList(List<User> users);
}
