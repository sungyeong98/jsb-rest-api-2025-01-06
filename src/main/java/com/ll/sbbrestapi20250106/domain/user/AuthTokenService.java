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

    public Map<String, Object> payload(String secret, String accessToken) {
        Map<String, Object> parsePayload = Ut.jwt.payload(secret, accessToken);

        if (parsePayload == null) return null;

        long id = (long) (Integer) parsePayload.get("id");
        String username = (String) parsePayload.get("username");

        return Map.of("id", id, "username", username);
    }

}
