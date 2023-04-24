package org.koreait.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter @MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false) // 수정 불가
    @CreatedDate // 앤티티 변화감지에 반응..
    private LocalDateTime regDt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false) // 추가 불가
    @LastModifiedDate
    private LocalDateTime modDt;
}
