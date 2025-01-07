package com.ll.sbbrestapi20250106.domain.user;

import com.ll.sbbrestapi20250106.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 50)
    private String password;

    @Column(length = 20)
    private String nickname;

    @Column(unique = true)
    private String email;

}
