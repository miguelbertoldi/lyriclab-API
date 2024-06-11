package com.lyriclab.lyriclab.service.user;

import com.lyriclab.lyriclab.model.dto.post.UserPostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthServiceTest {

//    @Autowired
//    private MockMvc mvc;

    @Test()
    void login() {
        UserPostDTO dto = new UserPostDTO(
                "username",
                "Full Name",
                "email@email.com",
                "1234");
//        assert dto.getEmail().isEmpty();
    }

}