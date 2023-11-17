package com.DXsprint.dockggu.service;


import com.DXsprint.dockggu.entity.FileEntity;
import com.DXsprint.dockggu.repository.FileRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MediaUpload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;
    @Autowired
    private AmazonS3 s3Client;

    private final FileRepository fileRepository;

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return convertedFile;
    }

    private static String getFileExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }

    public FileEntity uploadFile(MultipartFile file) {
        System.out.println(">>> MediaUpload.uploadFile");

        FileEntity fileEntity = new FileEntity();
        File fileObj = convertMultiPartFileToFile(file);
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();

        fileEntity.setFileName(fileName);
        fileEntity.setFileUrl(bucketName + ".s3.ap-northeast-2.amazonaws.com" + "/" +fileName);

        System.out.println("fileName : " + fileName);
        System.out.println("bucketName : " + bucketName);

        return fileEntity;

    }





    // https://jindevelopetravel0919.tistory.com/153
//    @Override
//    public Media uploadMedia(MultipartFile[] medias, String mediaPurpose) {
//        FileEntity fileEntity = new FileEntity();
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//
//
//        for(MultipartFile media : medias) {
//            String mediaName = createFileName(media.getOriginalFilename()); // 각 파일의 이름을 저장
//
//            objectMetadata.setContentLength(media.getSize());
//            objectMetadata.setContentType(media.getContentType());
//            System.out.println("for each 진입 : " + mediaName);
//
//            try (InputStream inputStream = media.getInputStream()) {
//                // S3에 업로드 및 저장
//                amazonS3.putObject(new PutObjectRequest(bucket, media.getOriginalFilename(), inputStream, objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
//            } catch (IOException e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
//            }
//
//            // 접근가능한 URL 가져오기
//            String mediaUrl = amazonS3.getUrl(bucket, mediaName).toString();
//
//            // 동시에 해당 미디어 파일들을 미디어 DB에 이름과 Url 정보 저장.
//            // 게시글마다 어떤 미디어 파일들을 포함하고 있는지 파악하기 위함 또는 활용하기 위함.
////        Media uploadMedia = Media.builder()
////                .mediaRealName(media.getOriginalFilename())
////                .mediaUidName(mediaName)
////                .mediaType(media.getContentType())
////                .mediaUrl(mediaUrl)
////                .mediaPurpose(mediaPurpose)
////                .build();
//
//            try {
//                fileEntity.setFileName(fileName);
//                fileEntity.setFileOriginalName(originalName);
//                fileEntity.setFileUrl(savePath.toString());
//
//                fileRepository.save(fileEntity);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//
//        return uploadMedia;
//    }
//
//    // S3에 저장되어있는 미디어 파일 삭제
//    @Override
//    public void deleteFile(String fileName) {
//        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
//    }
//
//
    // 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌린다. (현재는 굳이 난수화할 필요가 없어보여 사용하지 않음)
//    @Override
//    public String createFileName(String fileName) {
//        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
//    }
//
//
//    // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
//    @Override
//    public String getFileExtension(String fileName) {
//        try {
//            return fileName.substring(fileName.lastIndexOf("."));
//        } catch (StringIndexOutOfBoundsException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//        }
//    }
}