package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author : jaeha-dev (Git)
 * @title  : 타임 도메인 클래스
 * @memo   : 모든 Entity 클래스의 상위 클래스가 되어 생성/수정일을 관리한다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createDate; // Entity 가 생성되어 저장될 때의 시간

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 조회한 Entity 의 값을 변경할 때의 시간
}