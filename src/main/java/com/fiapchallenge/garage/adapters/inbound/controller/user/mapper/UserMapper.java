package com.fiapchallenge.garage.adapters.inbound.controller.user.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.UserDTO;
import com.fiapchallenge.garage.domain.user.User;

public class UserMapper {

    public static UserDTO toResponseDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getRole()
        );
    }
}