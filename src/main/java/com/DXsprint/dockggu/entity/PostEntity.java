package com.DXsprint.dockggu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Post")
@Table(name="TB_Post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String postId;
    private String postTitle;
    private String postContent;
    private String postImage;
    private String postVideo;
    private String postFile;
    private String postWriteDate;
    private String postLikesCnt;
}
