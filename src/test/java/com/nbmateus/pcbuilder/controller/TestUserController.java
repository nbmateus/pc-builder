package com.nbmateus.pcbuilder.controller;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.SignUpForm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signUpSuccessfully() throws Exception {

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new SignUpForm("newUser", "newUser@example.com", "newuserpass", "newuserpass"))))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());
    }

    @Test
    public void loginSuccessfully() throws Exception {

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new LoginForm("user1", "password1"))))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void loginInvalidCredentials() throws Exception {

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new LoginForm("user1", "password12"))))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.UNAUTHORIZED.value());
    }
    
}