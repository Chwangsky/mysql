package com.woo.boardback.service.implement;

import java.io.File;
import java.util.UUID;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woo.boardback.exception.exceptions.FileStorageException;
import com.woo.boardback.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    private String sourcePath = System.getProperty("user.dir") + "/board-back"; // TODO : DOCKER에 넣을 시, 수정!!!

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty())
            return null;

        String originalFileName = file.getOriginalFilename();

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;

        // Convert to relative path
        String savePath = Paths.get(sourcePath, filePath, saveFileName).toString();

        try {
            file.transferTo(new File(savePath));
        } catch (Exception e) {
            throw new FileStorageException("파일 전송에 실패했습니다. 에러메시지: " + e.getMessage());
        }

        String url = fileUrl + saveFileName;

        return url;
    }

    @Override
    public Resource getImage(String fileName) {
        Resource resource = null;
        String absoluteFilePath = Paths.get(sourcePath, filePath, fileName).toAbsolutePath().toString();

        try {
            // Convert the absolute path to a URI and then to a UrlResource
            resource = new UrlResource("file:" + absoluteFilePath);

            // Check if the resource exists and is readable
            // resource.isReadable() // is false
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileStorageException("파일을 읽을 수 없습니다. 현재 경로: " + "file:" + absoluteFilePath);
            }
        } catch (Exception e) {
            throw new FileStorageException(
                    "파일을 읽을 수 없습니다. 현재 경로: " + "file:" + absoluteFilePath + " 에러메시지: " + e.getMessage());
        }
        return resource;
    }
}
