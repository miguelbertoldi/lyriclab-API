package com.lyriclab.lyriclab.controller.user;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Criar usuário completo")
    void criarUsuarioCompleto() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.
                post("/auth/register").
                contentType(MediaType.APPLICATION_JSON).
                content("""
                    {
                        "username": "abc",
                        "fullName": "Usuario Nome 3",
                        "email": "abc",
                        "password": "123"
                    }
                """)).andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("Criar usuário sem username")
    void criarUsuarioSemUsername() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.
                post("/auth/register").
                contentType(MediaType.APPLICATION_JSON).
                content("""
                    {
                        "fullName": "Usuario Nome 3",
                        "email": "abc@gmail.com",
                        "password": "123"
                    }
                """)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    @DisplayName("Criar usuário com username já existente")
    void criarUsuarioComUsernameExistente() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post("/auth/register").
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                    {
                        "username": "abc",
                        "fullName": "Usuario Username Existente",
                        "email": "abc@gmail.com",
                        "password": "123"
                    }
                """)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName("Login com usuário completo")
    void loginUsuarioCompleto() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post("/auth/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                    {
                        "username": "abc",
                        "password": "123"
                    }
                """)).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @DisplayName("Login com senha incorreta")
    void loginComSenhaIncorreta() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post("/auth/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                    {
                        "username": "abc",
                        "password": "senha_incorreta"
                    }
                """)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    @DisplayName("Login com usuário inexistente")
    void loginUsuarioInexistente() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post("/auth/login").
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                    {
                        "username": "usuario_inexistente",
                        "password": "senha_incorreta"
                    }
                """)).andExpect(status().isBadRequest());
    }

}