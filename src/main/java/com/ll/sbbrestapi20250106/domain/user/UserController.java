package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.domain.user.dto.SiteUserDto;
import com.ll.sbbrestapi20250106.global.exceptions.ServiceException;
import com.ll.sbbrestapi20250106.global.rq.Rq;
import com.ll.sbbrestapi20250106.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Rq rq;

    record UserSignupReqBody(
            @NotBlank
            @Length(min = 3, max = 20)
            String username,
            @NotBlank
            @Length(min = 3, max = 50)
            String password,
            @NotBlank
            @Length(min = 2, max = 20)
            String nickname,
            @NotBlank
            String email
    ) {}



    @PostMapping("/sign-up")
    public RsData<SiteUserDto> signup(
            @Valid @RequestBody UserSignupReqBody reqBody
    ) {
        SiteUser user = userService.join(reqBody.username, reqBody.password, reqBody.nickname, reqBody.email);

        return new RsData<>(
                "201-1",
                "%s님, 환영합니다.".formatted(user.getNickname()),
                new SiteUserDto(user)
        );
    }

    record  UserLoginReqBody(
            String username,
            String password
    ) {}

    record UserLoginResBody(
            SiteUserDto item,
            String apiKey
    ) {}

    @PostMapping("/login")
    public RsData<UserLoginResBody> login(
            @RequestBody UserLoginReqBody reqBody
    ) {
        SiteUser user = userService
                .findByUsername(reqBody.username)
                .orElseThrow(() -> new ServiceException("409-1", "존재하지 않는 ID 입니다."));

        if(!user.matchPassword(reqBody.password)) {
            throw new ServiceException("401-2", "비밀번호가 일치하지 않습니다.");
        }

        return new RsData<>(
                "200-1",
                "%s님, 환영합니다.".formatted(user.getNickname()),
                new UserLoginResBody(
                        new SiteUserDto(user),
                        user.getApiKey()
                )
        );
    }

    @GetMapping("/profile")
    public SiteUserDto profile() {
        SiteUser user = rq.checkAuthentication();

        return new SiteUserDto(user);
    }

}
