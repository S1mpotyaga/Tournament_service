package org.tournament;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.entity.UserEntity;
import org.tournament.endpoints.mappers.UserMapper;
import org.tournament.endpoints.repositories.UserRepository;
import org.tournament.endpoints.services.UserService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;


    @Test
    public void shoudCreateUser(){
        UserDTO userDTO = new UserDTO();
        UserEntity userEntity = new UserEntity();
        when(userMapper.fromDTO(userDTO)).thenReturn(userEntity);
        userService.createUser(userDTO);
        verify(userRepository).save(userEntity);
    }
}
