package com.ll.sbbrestapi20250106.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long count() {
        return userRepository.count();
    }

    public User join(String username, String password, String nickname, String email) {
        userRepository
                .findByUsername(username)
                .ifPresent(_ -> {
                    throw new IllegalArgumentException("이미 존재하는 ID입니다.");
                });

        User user = User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();

        return userRepository.save(user);
    }

}
