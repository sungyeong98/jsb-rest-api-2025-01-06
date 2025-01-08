package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.domain.question.QuestionService;
import com.ll.sbbrestapi20250106.domain.question.dto.QuestionListDto;
import com.ll.sbbrestapi20250106.domain.user.dto.SiteUserDto;
import com.ll.sbbrestapi20250106.global.exceptions.ServiceException;
import com.ll.sbbrestapi20250106.global.rq.Rq;
import com.ll.sbbrestapi20250106.global.rsData.RsData;
import com.ll.sbbrestapi20250106.standard.page.PageDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final QuestionService questionService;
    private final UserService userService;
    private final Rq rq;
    private final AuthTokenService authTokenService;

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
    @Transactional
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
            @NotBlank
            String username,
            @NotBlank
            String password
    ) {}

    record UserLoginResBody(
            SiteUserDto item,
            String apiKey,
            String accessToken
    ) {}

    @PostMapping("/login")
    @Transactional(readOnly = true)
    public RsData<UserLoginResBody> login(
            @Valid @RequestBody UserLoginReqBody reqBody
    ) {
        SiteUser user = userService
                .findByUsername(reqBody.username)
                .orElseThrow(() -> new ServiceException("409-1", "존재하지 않는 ID 입니다."));

        if(!user.matchPassword(reqBody.password)) {
            throw new ServiceException("401-2", "비밀번호가 일치하지 않습니다.");
        }

        String accessToken = userService.genAccessToken(user);

        return new RsData<>(
                "200-1",
                "%s님, 환영합니다.".formatted(user.getNickname()),
                new UserLoginResBody(
                        new SiteUserDto(user),
                        user.getApiKey(),
                        accessToken
                )
        );
    }

    @GetMapping("/profile")
    @Transactional(readOnly = true)
    public SiteUserDto profile() {
        SiteUser user = rq.getActor();

        return new SiteUserDto(user);
    }

    @GetMapping("/profile/my-list")
    @Transactional(readOnly = true)
    public PageDto<QuestionListDto> myList(
            @RequestParam(defaultValue = "subject") String searchKeywordType,
            @RequestParam(defaultValue = "") String searchKeyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        SiteUser user = rq.getActor();

        return new PageDto<>(
                questionService.findByAuthorPaged(user, searchKeywordType, searchKeyword, page, pageSize)
                        .map(QuestionListDto::new)
        );
    }

    // 엑세스 토큰을 편하게 얻기 위해서 만든 임시 메서드
    // 추후 삭제 예정
    @GetMapping("/test")
    @Transactional(readOnly = true)
    public String test() {
        SiteUser user = userService.findByUsername("user1").get();

        String accessToken = userService.genAccessToken(user);

        return accessToken;
    }

}
