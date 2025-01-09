package com.ll.sbbrestapi20250106.global.security;

import com.ll.sbbrestapi20250106.domain.user.SiteUser;
import com.ll.sbbrestapi20250106.domain.user.UserService;
import com.ll.sbbrestapi20250106.global.rq.Rq;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final Rq rq;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring("Bearer ".length());
        String[] tokenBits = token.split(" ", 2);

        if (tokenBits.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = tokenBits[0];
        String accessToken = tokenBits[1];

        SiteUser user = userService.getUserFromAccessToken(accessToken);

        if (user == null) {
            Optional<SiteUser> opUserByApiKey = userService.findByApiKey(apiKey);

            if (opUserByApiKey.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            user = opUserByApiKey.get();

            String newAccessToken = userService.genAccessToken(user);

            response.setHeader("Authorization", "Bearer " + apiKey + " " + newAccessToken);
        }

        rq.setLogin(user);

        filterChain.doFilter(request, response);
    }

}
