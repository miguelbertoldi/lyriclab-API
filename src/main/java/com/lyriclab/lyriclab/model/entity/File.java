package com.lyriclab.lyriclab.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "tb_file")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public File(MultipartFile file)
            throws IOException {
        this.type = file.getContentType();
        this.name = file.getOriginalFilename();
        this.data = file.getBytes();
    }
}
