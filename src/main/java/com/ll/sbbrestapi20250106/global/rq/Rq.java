package com.ll.sbbrestapi20250106.global.rq;

import com.ll.sbbrestapi20250106.domain.user.SiteUser;
import com.ll.sbbrestapi20250106.domain.user.UserService;
import com.ll.sbbrestapi20250106.global.exceptions.ServiceException;
import com.ll.sbbrestapi20250106.standard.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {
    private final UserService userService;
    private final HttpServletRequest request;
    public SiteUser checkAuthentication() {
        String credentials = request.getHeader("Authorization");
        String apiKey = credentials == null ?
                ""
                :
                credentials.substring("Bearer ".length());
        if (Ut.str.isBlank(apiKey))
            throw new ServiceException("401-1", "apiKey를 입력해주세요.");
        Optional<SiteUser> opActor = userService.findByApiKey(apiKey);
        if (opActor.isEmpty())
            throw new ServiceException("401-1", "비밀번호가 일치하지 않습니다.");
        return opActor.get();
    }
}
