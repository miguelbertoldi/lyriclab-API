package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public File save(MultipartFile file) {
        try {
            return fileRepository.save(
                    new File(file)
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public File loadImageAsFile(String resourcePath) {
        try {
            byte[] file = Files.readAllBytes(Paths.get(resourcePath));
            return fileRepository.save(
                    new File(file)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(Long id) {
        if (!fileRepository.existsById(id)) {
            throw new RuntimeException("File doesn't exist");
        }
        fileRepository.deleteById(id);
    }

}
