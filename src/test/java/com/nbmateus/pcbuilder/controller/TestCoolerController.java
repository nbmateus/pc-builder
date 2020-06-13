package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.model.Cooler;

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
@Sql("/coolerData.sql")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestCoolerController {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testGetAll() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/cooler/all").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                Cooler[] coolerList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                Cooler[].class);
                assertTrue(coolerList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetCoolerByIdSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/cooler/3").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                Cooler coolerId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cooler.class);

                assertTrue(coolerId3.getId() == 3);
        }

        @Test
        public void testGetCoolerByIdNotFound() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/cooler/9").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddCoolerSuccessfully() throws Exception {
                Cooler cooler = new Cooler("coolerMaker", "coolerName1", 1, 2, 1, 1, 1);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cooler/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(cooler))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddCoolerDuplicated() throws Exception {
                Cooler cooler = new Cooler("coolerMaker1", "coolerName2", 2, 2, 2, 2, 2);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cooler/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(cooler))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateCoolerSuccesfully() throws Exception {

                Cooler coolerId4 = new Cooler("coolerMaker2", "coolerName4", 2, 1, 3, 2, 2);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update Cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cooler/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(coolerId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdateCoolerDuplicated() throws Exception {

                Cooler coolerId4 = new Cooler("coolerMaker1", "coolerName1", 2, 2, 2, 2, 2);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cooler/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(coolerId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateCoolerNotFound() throws Exception {

                Cooler cooler = new Cooler("asdasd", "aaaadrfgads", 2, 1, 3, 2, 2);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update Cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cooler/8")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cooler))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeleteCoolerSuccessfully() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cooler/1").header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage()))
                                .andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeleteCoolerNotFound() throws Exception {
                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                //delete cooler
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cooler/10")
                .header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage())).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testGetCoolerListByCpuTdp() throws Exception {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cooler/minimum-tdp/70")
                                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

                Cooler[] coolerList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                Cooler[].class);

                assertTrue(coolerList.length == 2 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
                for (Cooler cooler : coolerList) {
                        assertTrue(cooler.getMaxCpuTDP() >= 70);
                }
        }
}