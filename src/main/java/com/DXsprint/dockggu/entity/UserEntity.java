package com.DXsprint.dockggu.entity;

import com.DXsprint.dockggu.dto.SignUpDto;
import com.DXsprint.dockggu.global.JpaAuditing.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor     // getter setter 역할
@NoArgsConstructor      // 위
@Entity(name="user")    // Entity로 지정
@Table(name="tb_user")     // DB table mapping
public class UserEntity extends BaseEntity {
    @Id     // userId를 PK로 쓰겠다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userEmail;
    private String userKakaoEmail;
    private String userPassword;
    private String userNickname;
    private String userProfileImgName;
    private String userProfileImgPath;
    private Integer userAward;
    private String userState;

    // 앞단에서 가져온 User 정보 쉽게 쓰기 위함
    public UserEntity(SignUpDto dto) {
        this.userEmail = dto.getUserEmail();
        this.userKakaoEmail = dto.getUserKakaoEmail();
        this.userPassword = dto.getUserPassword();
        this.userNickname = dto.getUserNickname();
    }
}
