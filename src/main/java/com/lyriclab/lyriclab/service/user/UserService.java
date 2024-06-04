package com.lyriclab.lyriclab.service.user;

import com.lyriclab.lyriclab.model.dto.get.PlaylistGetDto;
import com.lyriclab.lyriclab.model.dto.get.user.UserGetDto;
import com.lyriclab.lyriclab.model.dto.get.user.UserBasicInfoDto;
import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.repository.UserRepository;
import com.lyriclab.lyriclab.service.FileService;
import com.lyriclab.lyriclab.util.AuthUserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    private final AuthUserUtil authUtil;

    protected UserGetDto save(UserCreationDTO dto) {
        try {
            User user = new User(dto);
            saveDefaultImage(user);
            return userRepository.save(user).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveDefaultImage(User user) {
        user.setPicture(
                fileService.loadImageAsFile("src/main/resources/images/user-default.jpg")
        );
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
            return userRepository
                    .save(user).toDto();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Boolean existsByEmailAndUsername(
            String email, String username) {
        return userRepository.existsByEmail(email)
                || userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public PlaylistGetDto findLikedLoggedMusics() {
        return authUtil.getAuthenticatedUser()
                .toDto().getPlaylists()
                .get(0);
    }

    public UserBasicInfoDto findLoggedUser() {
        return authUtil.getAuthenticatedUser().getBasicInfo();
    }

    public UserGetDto findLoggedUserComplete() {
        return authUtil.getAuthenticatedUser().toDto();
    }

}
