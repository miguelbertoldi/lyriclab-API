package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void remove(Long id) {
        if (!fileRepository.existsById(id)) {
            throw new RuntimeException("File doesn't exist");
        }
        fileRepository.deleteById(id);
    }

}
