package com.freelanceplatform.DAL.Mapper;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.DTO.UserRole;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-01T17:39:58+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(CreateUserDTO createUserDTO) {
        if ( createUserDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( createUserDTO.getName() );
        user.email( createUserDTO.getEmail() );
        user.password( createUserDTO.getPassword() );
        user.role( createUserDTO.getRole() );

        return user.build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.password( userDTO.getPassword() );
        if ( userDTO.getRole() != null ) {
            user.role( Enum.valueOf( UserRole.class, userDTO.getRole() ) );
        }

        return user.build();
    }

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        if ( user.getRole() != null ) {
            userDTO.setRole( user.getRole().name() );
        }

        return userDTO;
    }
}
