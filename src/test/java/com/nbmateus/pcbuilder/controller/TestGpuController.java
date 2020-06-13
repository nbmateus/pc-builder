package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.model.GPU;

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
@Sql("/gpuData.sql")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestGpuController {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testGetAll() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/gpu/all").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                GPU[] gpuList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GPU[].class);
                assertTrue(gpuList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetGpuByIdSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/gpu/3").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                GPU gpuId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GPU.class);

                assertTrue(gpuId3.getId() == 3);
        }

        @Test
        public void testGetGpuByIdNotFound() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/gpu/9").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddGpuSuccessfully() throws Exception {
                GPU gpu = new GPU("gpuMaker1", "gpuName5", 1, 1, 1, 1, 1, 1, true, true, false, false);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add gpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/gpu/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(gpu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddGpuDuplicated() throws Exception {
                GPU gpu = new GPU("gpuMaker2", "gpuName3", 1, 1, 1, 1, 1, 1, true, true, false, false);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add gpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/gpu/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(gpu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateGpuSuccesfully() throws Exception {

                GPU gpuId4 = new GPU("gpuMaker4", "gpuName4", 1, 2, 2, 1, 1, 1, false, false, false, false);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update gpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/gpu/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(gpuId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdateGpuDuplicated() throws Exception {

                GPU gpuId4 = new GPU("gpuMaker1", "gpuName2", 1, 1, 1, 1, 1, 1, true, true, false, false);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update gpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/gpu/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(gpuId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateGpuNotFound() throws Exception {
                GPU gpu = new GPU("gpumaker", "gpuname", 1, 2, 2, 1, 1, 1, false, false, false, false);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update gpu
                mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/gpu/8").header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(gpu)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeleteGpuSuccessfully() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete gpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/gpu/1").header("Authorization", objectMapper
                                .readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class)
                                .getMessage())).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeleteGpuNotFound() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete gpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/gpu/10").header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage()))
                                .andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }
}