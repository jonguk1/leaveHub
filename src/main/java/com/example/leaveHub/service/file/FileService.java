package com.example.leaveHub.service.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${file.upload-path}")
    private String uploadDir;

    /**
     * 설정된 업로드 기본 경로를 반환 (DB 저장용)
     */
    public String getUploadDir() {
        return uploadDir;
    }

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            return null;

        // 원본 파일명 및 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 서버 저장용 고유 이름 생성
        String storedFileName = UUID.randomUUID().toString() + extension;

        // 디렉토리 존재 여부 확인 및 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 실제 파일 저장
        try {
            // File 객체로 경로 결합을 안전하게 처리
            File targetFile = new File(directory, storedFileName);
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다. 경로: " + uploadDir, e);
        }

        return storedFileName;
    }
}