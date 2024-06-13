package com.lyriclab.lyriclab.service.user;

import com.lyriclab.lyriclab.model.dto.get.user.*;

import com.lyriclab.lyriclab.model.entity.File;
import com.lyriclab.lyriclab.model.dto.get.PlaylistResponseDto;
import com.lyriclab.lyriclab.model.dto.get.user.UserBasicInfoDto;
import com.lyriclab.lyriclab.model.dto.post.ArtistPostDTO;
import com.lyriclab.lyriclab.model.dto.post.UserPostDTO;
import com.lyriclab.lyriclab.model.entity.user.Artist;
import com.lyriclab.lyriclab.model.entity.user.User;
import com.lyriclab.lyriclab.model.enums.UserKind;
import com.lyriclab.lyriclab.repository.ArtistRepository;
import com.lyriclab.lyriclab.repository.UserRepository;
import com.lyriclab.lyriclab.service.FileService;
import com.lyriclab.lyriclab.util.AuthUserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final FileService fileService;

    private final AuthUserUtil authUtil;

    protected UserResponseDto save(UserPostDTO dto) {
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

    public UserResponseDto findById(Long id) {
        return new UserResponseDto(
                findEntityById(id)
        );
    }

    public UserResponseDto uploadFile(
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

    public Boolean existsByEmail(UserEmailDTO userEmailDTO) {
            return userRepository.existsByEmail(userEmailDTO.getEmail());

    }

    public void editPassword(UserPasswordDTO userPasswordDTO){
        User user = userRepository.findByEmail(userPasswordDTO.getEmail()).get();
        user.setPassword(userPasswordDTO.getPassword());
        userRepository.save(user);
    }

    public PlaylistResponseDto findLikedLoggedMusics() {
        return authUtil.getAuthenticatedUser()
                .toDto().getPlaylists()
                .get(0);
    }

    public UserBasicInfoDto findLoggedUser() {
        return authUtil.getAuthenticatedUser().getBasicInfo();
    }

    public UserResponseDto findLoggedUserComplete() {
        return authUtil.getAuthenticatedUser().toDto();
    }

    public void editUser(UserEditDto userEditDto){
        User user = userRepository.findById(userEditDto.getId()).get();
        user.setEmail(userEditDto.getEmail());
        user.setUsername(userEditDto.getUsername());
        save(user);
    }
    public void makeUserAnArtist() {
        User user = authUtil.getAuthenticatedUser();
        user.setUserKind(UserKind.ARTIST);
        save(user);
    }

    public User findArtist(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()
                && user.get().getUserKind() == UserKind.ARTIST) {
            return user.get();
        }

        throw new RuntimeException("User doesn't exist or isn't an artist!");
    }
}
