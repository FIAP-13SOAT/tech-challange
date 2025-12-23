package com.fiapchallenge.garage.application.user;

import com.fiapchallenge.garage.application.user.command.LoginUserCommand;
import com.fiapchallenge.garage.shared.jwt.JwtHelper;
import com.fiapchallenge.garage.infra.UserDetailsImpl;
import com.fiapchallenge.garage.shared.jwt.JwtTokenVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LoginUserService implements LoginUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    public LoginUserService(AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    public JwtTokenVO handle(LoginUserCommand cmd) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cmd.email(), cmd.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        assert userDetailsImpl != null;

        return jwtHelper.generateToken(userDetailsImpl);
    }
}
