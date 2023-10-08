package com.DXsprint.dockggu.repository;

import com.DXsprint.dockggu.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByFileId(Long id);

}
