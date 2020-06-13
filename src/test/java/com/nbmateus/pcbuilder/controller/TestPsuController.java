package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.model.PSU;
import com.nbmateus.pcbuilder.model.PsuCertification;

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
@Sql("/psuData.sql")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestPsuController {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testGetAll() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/psu/all").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                PSU[] psuList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PSU[].class);
                assertTrue(psuList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetPsuByIdSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/psu/3").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                PSU psuId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PSU.class);

                assertTrue(psuId3.getId() == 3);
        }

        @Test
        public void testGetPsuByIdNotFound() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/psu/9").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddPsuSuccessfully() throws Exception {
                PSU psu = new PSU("psuMaker1", "psuName5", 1, 1, PsuCertification.GOLD, 1);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/psu/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(psu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddPsuDuplicated() throws Exception {
                PSU psu = new PSU("psuMaker2", "psuName3", 1, 1, PsuCertification.GOLD, 1);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/psu/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(psu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdatePsuSuccesfully() throws Exception {

                PSU psuId4 = new PSU("psuMaker2", "psuName4", 1, 1, PsuCertification.GOLD, 1);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/psu/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(psuId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdatePsuDuplicated() throws Exception {

                PSU psuId4 = new PSU("psuMaker1", "psuName2", 1, 1, PsuCertification.GOLD, 1);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/psu/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(psuId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdatePsuNotFound() throws Exception {
                PSU psu = new PSU("psumak", "psunam", 1, 1, PsuCertification.GOLD, 1);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/psu/8")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(psu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeletePsuSuccessfully() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/psu/1")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeletePsuNotFound() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete psu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/psu/10")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }
}