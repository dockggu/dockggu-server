package com.DXsprint.dockggu.global.JpaAuditing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
@Getter
@ToString
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime objectCreateDate;

    @LastModifiedDate
    private LocalDateTime objectUpdateDate;
}