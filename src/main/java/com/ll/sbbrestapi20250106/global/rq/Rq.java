package com.ll.sbbrestapi20250106.global.rq;

import com.ll.sbbrestapi20250106.domain.user.SiteUser;
import com.ll.sbbrestapi20250106.domain.user.UserService;
import com.ll.sbbrestapi20250106.global.exceptions.ServiceException;
import com.ll.sbbrestapi20250106.standard.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {

    private final UserService userService;

    public SiteUser getActor() {
        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();

        if (authentication == null) return null;

        if (authentication.getPrincipal() == null || authentication.getPrincipal() instanceof String) return null;

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String username = user.getUsername();

        return userService.findByUsername(username).get();
    }

    public SiteUser getActorByUsername(String username) {
        return userService.findByUsername(username).get();
    }

    public void setLogin(String username) {
        UserDetails user = new User(
                username,
                "",
                List.of()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
