package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.model.CPU;
import com.nbmateus.pcbuilder.model.Socket;

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
@Sql("/cpuData.sql")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestCpuController {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testGetAll() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/cpu/all").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                CPU[] cpuList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CPU[].class);
                assertTrue(cpuList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetCpuByIdSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/cpu/3").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                CPU cpuId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CPU.class);

                assertTrue(cpuId3.getId() == 3);
        }

        @Test
        public void testGetCpuByIdNotFound() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/cpu/9").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddCpuSuccessfully() throws Exception {
                CPU cpu = new CPU("cpuMaker1", "nameTest5", 1, 1, 1, 1, 1, 1, 1, Socket.LGA1200, false, false, false);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add cpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cpu/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(cpu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddCpuDuplicated() throws Exception {
                CPU cpu = new CPU("cpuMaker1", "nameTest2", 2, 2, 2, 2, 2, 2, 2, Socket.FM2plus, true, true, true);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add cpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cpu/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(cpu))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateCpuSuccesfully() throws Exception {

                CPU cpuId4 = new CPU("cpuMaker2", "nameTest4", 2, 2, 1, 2, 1, 2, 2, Socket.AM4, true, false, true);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update cpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cpu/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cpuId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdateCpuDuplicated() throws Exception {

                CPU cpuId4 = new CPU("cpuMaker2", "nameTest3", 2, 2, 2, 2, 2, 2, 2, Socket.LGA1200, true, true, true);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update cpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cpu/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cpuId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateCpuNotFound() throws Exception {

                CPU cpu = new CPU("cpuMaker2", "nameTest4", 2, 2, 1, 2, 1, 2, 2, Socket.AM4, true, false, true);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update cpu
                mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/cpu/8").header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(cpu)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeleteCpuSuccessfully() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete cpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cpu/1").header("Authorization", objectMapper
                                .readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class)
                                .getMessage())).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeleteCpuNotFound() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete cpu
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cpu/10").header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage()))
                                .andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void getCpuListByMaker() throws Exception {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cpu/made-by/cpuMaker2")
                                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

                CPU[] cpuList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CPU[].class);

                assertTrue(cpuList.length == 2 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
                for (CPU cpu : cpuList) {
                        assertTrue(cpu.getMaker().equals("cpuMaker2"));
                }
        }
}