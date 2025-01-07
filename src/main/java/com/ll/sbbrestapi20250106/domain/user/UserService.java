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
        User user = User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();

        return userRepository.save(user);
    }

}
