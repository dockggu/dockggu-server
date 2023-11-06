package com.DXsprint.dockggu.controller;

import com.DXsprint.dockggu.dto.ResponseDto;
import com.DXsprint.dockggu.dto.FileResponseDto;
import com.DXsprint.dockggu.entity.FileEntity;
import com.DXsprint.dockggu.repository.FileRepository;
import com.DXsprint.dockggu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/file/")
public class FIleController {

    @Autowired
    FileService fileService;
    @Autowired
    FileRepository fileRepository;

    @Value("${com.DXsprint.upload.path}") //application.properties의 변수
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseDto<?> uploadFile(MultipartFile[] uploadFiles){
        System.out.println(">>> FileController.uploadFile");

        List<FileResponseDto> resultDtoList = new ArrayList<>();
        FileEntity fileEntity = new FileEntity();

        //MultipartFile은 단건만 배열로 설정하면 다수의 파일을 받을 수있습니다.
        //배열을 활용하면 동시에 여러개의 파일 정보를 처리할 수 있으므로 화면에서 여러개의 파일을 동시에 업로드 할 수 있습니다.
        for(MultipartFile uploadFile : uploadFiles){
            if(uploadFile.getContentType().startsWith("image") == false) {
                return ResponseDto.setFailed("this file is not image type");
            }

            //브라우저에 따라 업로드하는 파일의 이름은 전체경로일 수도 있고(Internet Explorer),
            //단순히 파일의 이름만을 의미할 수도 있습니다.(chrome browser)
            String originalName = uploadFile.getOriginalFilename();//파일명:모든 경로를 포함한 파일이름
            String fileName = originalName.substring(originalName.lastIndexOf("//")+1);
            //예를 들어 getOriginalFileName()을 해서 나온 값이 /Users/Document/bootEx 이라고 한다면
            //"마지막으로온 "/"부분으로부터 +1 해준 부분부터 출력하겠습니다." 라는 뜻입니다.따라서 bootEx가 됩니다.
            System.out.println("fileName : " + fileName);
            //날짜 폴더 생성
            String folderPath = makeFolder();
            //UUID
            String uuid = UUID.randomUUID().toString();
            //저장할 파일 이름 중간에 "_"를 이용하여 구분
            String saveName = uploadPath + File.separator +File.separator + uuid + "_" + fileName;
            fileName = uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);
            //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)

            System.out.println("originalName : " + originalName);
            System.out.println("fileName : " + fileName);
            System.out.println("saveName : " + saveName);
            System.out.println("savePath : " + savePath);

            try{
                uploadFile.transferTo(savePath);
                //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)

                resultDtoList.add(new FileResponseDto(fileName, uuid, folderPath));

                fileEntity.setFileName(fileName);
                fileEntity.setFileOriginalName(originalName);
                fileEntity.setFileUrl(savePath.toString());
                fileRepository.save(fileEntity);

            } catch (IOException e) {
                e.printStackTrace();
                //printStackTrace()를 호출하면 로그에 Stack trace가 출력됩니다.
            }
        }//end for
        return ResponseDto.setSuccess("Success to save file", resultDtoList);
    }

    private String makeFolder(){

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //LocalDate를 문자열로 포멧
        String folderPath = str.replace("/", File.separator);
        //만약 Data 밑에 exam.jpg라는 파일을 원한다고 할때,
        //윈도우는 "Data\\"eaxm.jpg", 리눅스는 "Data/exam.jpg"라고 씁니다.
        //그러나 자바에서는 "Data" +File.separator + "exam.jpg" 라고 쓰면 됩니다.

        //make folder ==================
        File uploadPathFolder = new File(uploadPath);
//        File uploadPathFolder = new File(uploadPath);
        //File newFile= new File(dir,"파일명");
        //->부모 디렉토리를 파라미터로 인스턴스 생성

        if(uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
            //만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
            //mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
        }
        System.out.println("Folder Path : " + folderPath);
        System.out.println(uploadPath);
        System.out.println(uploadPathFolder.toString());
        return folderPath;
    }
}

