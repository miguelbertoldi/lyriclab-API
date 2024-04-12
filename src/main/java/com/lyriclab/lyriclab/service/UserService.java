package com.lyriclab.lyriclab.service;

import com.lyriclab.lyriclab.model.dto.get.UserGetDto;
import com.lyriclab.lyriclab.model.dto.post.PlaylistCreationDTO;
import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.entity.Playlist;
import com.lyriclab.lyriclab.model.entity.User;
import com.lyriclab.lyriclab.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    public UserGetDto register(UserCreationDTO userCreationDTO) {
        if (userRepository.existsByEmail(userCreationDTO.getEmail())) {
            if (userRepository.existsByUsername(userCreationDTO.getUsername())) {
                throw new EntityExistsException("Nome de usuário em uso!");
            }
            throw new EntityExistsException("Email em uso!");
        }
        return saveUserAndConvertToDto(new User(userCreationDTO));
    }

    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public UserGetDto findById(Long id) {
        return new UserGetDto(
                findEntityById(id)
        );
    }

    public UserGetDto uploadFile(
            Long id, MultipartFile file) {
        try {
            User user = findEntityById(id);
            user.setPicture(fileService.save(file));
            return saveUserAndConvertToDto(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private UserGetDto saveUserAndConvertToDto(User user) {
        return new UserGetDto(
                userRepository.save(user)
        );
    }

}
