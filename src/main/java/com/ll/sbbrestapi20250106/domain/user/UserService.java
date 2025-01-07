package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long count() {
        return userRepository.count();
    }

    public SiteUser join(String username, String password, String nickname, String email) {
        userRepository
                .findByUsername(username)
                .ifPresent(_ -> {
                    throw new ServiceException("400-1", "이미 존재하는 ID입니다.");
                });

        SiteUser user = SiteUser.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();

        return userRepository.save(user);
    }

    public Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<SiteUser> findByApiKey(String apiKey) {
        return userRepository.findByApiKey(apiKey);
    }

}
