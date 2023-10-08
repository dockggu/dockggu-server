package com.DXsprint.dockggu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_file")
@Table(name = "tb_file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;
    private String fileOriginalName;
    private String fileUrl;
    private Long fileSize;

    public FileEntity(long fileID, String fileName, String fileOriginalName, long fileSize) {
        this.fileId = fileID;
        this.fileName = fileName;
        this.fileOriginalName = fileOriginalName;
        this.fileSize = fileSize;
    }
}
