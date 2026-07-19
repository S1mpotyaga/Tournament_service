package org.tournament.endpoints.mappers;

import org.springframework.stereotype.Component;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.entity.UserEntity;
import org.tournament.endpoints.ConverterException;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public UserEntity fromDTO(UserDTO user) throws ConverterException {
        try {
            UserEntity result = new UserEntity();
            result.setEmail(user.getEmail());
            result.setNick(user.getNick());
            result.setRole(user.getRole());
            result.setPassword(user.getPassword());
            result.setFullName(user.getFullName());
            if (user.getRegistrationTime() == null){
                result.setRegistrationDate(LocalDateTime.now());
            }else {
                result.setRegistrationDate(user.getRegistrationTime());
            }
            return result;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from dto:" + user.toString() + " - to entity.");
        }
    }

    public UserDTO fromEntity(UserEntity user) throws ConverterException{
        try {
            UserDTO result = new UserDTO();
            result.setUserId(user.getUserId());
            result.setEmail(user.getEmail());
            result.setNick(user.getNick());
            result.setRole(user.getRole());
            result.setPassword(user.getPassword());
            result.setFullName(user.getFullName());
            result.setRegistrationTime(user.getRegistrationDate());
            return result;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from entity: " + user.toString() + " - to dto.");
        }
    }
}
