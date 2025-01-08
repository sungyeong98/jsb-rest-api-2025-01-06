package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.standard.util.Ut;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {

    public String genAccessToken(SiteUser user) {
        long id = user.getId();
        String username = user.getUsername();

        return Ut.jwt.toString(
                "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890",
                60 * 60 * 24 * 365,
                Map.of("id", id, "username", username)
        );
    }

}
