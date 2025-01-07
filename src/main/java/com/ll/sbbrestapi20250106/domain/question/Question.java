package com.ll.sbbrestapi20250106.domain.question;

import com.ll.sbbrestapi20250106.domain.base.BaseTime;
import com.ll.sbbrestapi20250106.domain.user.SiteUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

}
