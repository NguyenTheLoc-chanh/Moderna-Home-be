package com.modernadev.Moderna.Home.security;

import com.modernadev.Moderna.Home.entity.User;
import com.modernadev.Moderna.Home.exception.NotFoundException;
import com.modernadev.Moderna.Home.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found with Email: " + username));
        return UserAuth.builder()
                .user(user)
                .build();
    }
}
