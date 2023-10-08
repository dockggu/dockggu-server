package com.DXsprint.dockggu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MybookDto {
    private Long userId;
    private Long bookertonId;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private int bookTotalPage;
    private int bookReadPage;
    private String bookImgName;
    private String bookImgPath;
}
