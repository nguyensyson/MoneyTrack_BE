package com.money.moneytrack_be.service;

import com.money.moneytrack_be.entity.User;
import com.money.moneytrack_be.exception.BadRequestException;
import com.money.moneytrack_be.repository.UserRepository;
import com.money.moneytrack_be.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(String email, String password, String name) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException("Email already in use: " + email);
        }
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return jwtUtil.generateToken(email);
    }
}
