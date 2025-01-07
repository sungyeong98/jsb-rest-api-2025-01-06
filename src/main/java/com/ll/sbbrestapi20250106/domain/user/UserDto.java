package com.ll.sbbrestapi20250106.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDto {

    private long id;
    @JsonProperty("createdDatetime")
    private LocalDateTime createDate;
    @JsonProperty("modifiedDatetime")
    private LocalDateTime modifyDate;
    private String username;
    private String password;
    private String nickname;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.createDate = user.getCreateDate();
        this.modifyDate = user.getModifyDate();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

}
