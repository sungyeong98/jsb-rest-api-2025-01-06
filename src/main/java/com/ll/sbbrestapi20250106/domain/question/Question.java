package com.ll.sbbrestapi20250106.domain.question;

import com.ll.sbbrestapi20250106.domain.base.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseTime {

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

}
