package com.DXsprint.dockggu.entity;

import com.DXsprint.dockggu.dto.MybookDto;
import com.DXsprint.dockggu.global.JpaAuditing.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_mybook")
@Table(name = "tb_mybook")
public class MybookEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private Long userId;
    private Long bookertonId;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private int bookTotalPage;
    private int bookReadPage;
    private String bookImgName;
    private String bookImgPath;


    public MybookEntity(MybookDto mybookDto) {
        this.userId = mybookDto.getUserId();
        this.bookertonId = mybookDto.getBookertonId();
        this.bookName = mybookDto.getBookName();
        this.bookAuthor = mybookDto.getBookAuthor();
        this.bookPublisher = mybookDto.getBookPublisher();
        this.bookTotalPage = mybookDto.getBookTotalPage();
        this.bookReadPage = mybookDto.getBookReadPage();
        this.bookImgName = null;
        this.bookImgPath = null;
    }
}
