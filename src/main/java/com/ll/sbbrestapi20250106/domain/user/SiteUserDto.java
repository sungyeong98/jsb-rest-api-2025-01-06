package com.ll.sbbrestapi20250106.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SiteUserDto {

    private long id;

    @JsonProperty("createdDatetime")
    private LocalDateTime createDate;

    @JsonProperty("modifiedDatetime")
    private LocalDateTime modifyDate;


    private String nickname;

    private String email;

    public SiteUserDto(SiteUser user) {
        this.id = user.getId();
        this.createDate = user.getCreateDate();
        this.modifyDate = user.getModifyDate();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

}
