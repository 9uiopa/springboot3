package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.User;
import org.example.springbootdeveloper.dto.AddUserRequest;
import org.example.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    //폼로그인 - 직접 입력 가입
    public Long save(AddUserRequest dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // bCryptPasswordEncoder로 패스워드 암호화
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }
    //oauth
    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public Optional<User> findByEmail(String email){
        System.out.println("findByEmail");
        return userRepository.findByEmail(email);
    }


}
