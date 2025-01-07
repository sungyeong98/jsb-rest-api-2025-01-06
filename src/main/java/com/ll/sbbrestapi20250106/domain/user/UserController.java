package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

}
