package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.constants.UserType;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name="idx_userNm", columnList = "userNm"), // 인덱스, 오름차순
        @Index(name="idx_regDt_desc", columnList = "regDt DESC") // 인덱스, 내림차순
}) // 컬럼 인덱스 걸어주기.
public class Users extends BaseEntity {
    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userNo; // 회원번호
    @Column(length=40, unique = true, nullable = false)
    private String userId; // 아이디
    @Column(length=65, nullable = false)
    private String userPw; // 비밀번호
    @Column(length=45, nullable = false)
    private String userNm; // 회원명
    @Column(length=100)
    private String email; // 이메일
    @Column(length=11)
    private String mobile; // 휴대전화번호
    @Enumerated(EnumType.STRING) // 상수에 숫자가 default 로 @EnumType.STRING 문자열로 바꿔줌
    @Column(length=20, nullable = false)
    private UserType type = UserType.USER;
}
