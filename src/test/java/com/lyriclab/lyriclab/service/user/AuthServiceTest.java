package com.lyriclab.lyriclab.service.user;

import com.lyriclab.lyriclab.model.dto.post.UserCreationDTO;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthServiceTest {

//    @Autowired
//    private MockMvc mvc;

    @Test()
    void login() {
        UserCreationDTO dto = new UserCreationDTO(
                "username",
                "Full Name",
                "email@email.com",
                "1234");
//        assert dto.getEmail().isEmpty();
    }

}