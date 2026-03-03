package com.fiapchallenge.garage.presenters.user;

import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.LoginUserResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.UserDTO;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.infra.JwtTokenVO;

public class UserPresenter {

    public UserDTO present(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getRole()
        );
    }

    public LoginUserResponseDTO present(JwtTokenVO tokenVO) {
        return new LoginUserResponseDTO(tokenVO.token(), tokenVO.expiration());
    }
}
