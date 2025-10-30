package com.fiapchallenge.garage.infra;

import com.fiapchallenge.garage.application.user.UserNotFoundException;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("O Usuário não foi encontrado"));

        var authorities = List.of(new SimpleGrantedAuthority("ADM"));

        return new UserDetailsImpl(user, authorities);
    }
}
