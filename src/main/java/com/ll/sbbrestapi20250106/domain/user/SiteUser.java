package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "SiteUser")
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser extends BaseTime {

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 50)
    private String password;

    @Column(length = 20)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 50)
    private String apiKey;

    public String getNickname() {
        return nickname;
    }

    public boolean isAdmin() {
        return "admin".equals(username);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public SiteUser(long id, String username) {
        this.setId(id);
        this.username = username;
    }

}
