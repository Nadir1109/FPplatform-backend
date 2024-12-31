package com.freelanceplatform.DAL.Mapper;

import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.DAL.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserDTO createUserDTO); // Bestaande methode
    User toEntity(UserDTO userDTO); // Nieuwe methode
    UserDTO toDTO(User user);
}
