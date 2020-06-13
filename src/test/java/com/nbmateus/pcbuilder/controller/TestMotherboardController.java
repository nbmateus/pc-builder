package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.CpuDto;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.model.Motherboard;
import com.nbmateus.pcbuilder.model.MotherboardSize;
import com.nbmateus.pcbuilder.model.RamType;
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
@Sql("/motherboardData.sql")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestMotherboardController {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testGetAllMotherboards() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/motherboard/all").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetMotherboardByIdSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/motherboard/3").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                Motherboard motherboardId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                Motherboard.class);

                assertTrue(motherboardId3.getId() == 3);
        }

        @Test
        public void testGetMotherboardByIdNotFound() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/motherboard/9").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddMotherboardSuccessfully() throws Exception {
                Motherboard motherboard = new Motherboard("motherboardMaker2", "motherboardName5", 1, 1,
                                MotherboardSize.ATX, Socket.TR4, RamType.DDR4, 4, 3400, true, 1, 4);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add motherboard
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/motherboard/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(motherboard))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddMotherboardDuplicated() throws Exception {
                Motherboard motherboard = new Motherboard("motherboardMaker1", "motherboardName2", 1, 1,
                                MotherboardSize.ATX, Socket.TR4, RamType.DDR4, 4, 3400, true, 1, 4);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add motherboard
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/motherboard/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(motherboard))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateMotherboardSuccesfully() throws Exception {

                Motherboard motherboardId4 = new Motherboard("motherboardMaker2", "motherboardNAme4", 2, 2,
                                MotherboardSize.ITX, Socket.TR4, RamType.DDR4, 4, 3400, true, 1, 4);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update motherboard
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/motherboard/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(motherboardId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdateMotherboardDuplicated() throws Exception {

                Motherboard motherboardId4 = new Motherboard("motherboardMaker2", "motherboardName3", 1, 1,
                                MotherboardSize.ATX, Socket.AM4, RamType.DDR4, 4, 3400, true, 1, 4);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update motherboard
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/motherboard/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(motherboardId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateMotherboardNotFound() throws Exception {
                Motherboard mobo = new Motherboard("motherboardMaker2", "motherboardNAme4", 2, 2, MotherboardSize.ITX,
                                Socket.TR4, RamType.DDR4, 4, 3400, true, 1, 4);

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update motherboard
                mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/motherboard/8").header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(mobo)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeleteMotherboardSuccessfully() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete motherboard
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/motherboard/1").header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage()))
                                .andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeleteMotherboardNotFound() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete motherboard
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/motherboard/10").header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage()))
                                .andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testGetMotherboardBySocket() throws Exception {

                CpuDto cpuDto = new CpuDto();
                cpuDto.setSocket(Socket.AM4);

                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/motherboard/filtered-by-cpu-specs")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(cpuDto))).andReturn();

                Motherboard[] moboList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                Motherboard[].class);

                assertTrue(moboList.length == 3 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
                for (Motherboard motherboard : moboList) {
                        assertTrue(motherboard.getSocket() == Socket.AM4);
                }

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetMotherboardBySocketAndUnlockedMultiplier() throws Exception {

                CpuDto cpuDto = new CpuDto();
                cpuDto.setSocket(Socket.AM4);
                cpuDto.setUnlockedMultiplier(true);

                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/motherboard/filtered-by-cpu-specs")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(cpuDto))).andReturn();

                Motherboard[] moboList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                Motherboard[].class);

                assertTrue(moboList.length == 1 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
                for (Motherboard motherboard : moboList) {
                        assertTrue(motherboard.getSocket() == Socket.AM4 && motherboard.isAllowsOverclock() == true);
                }
        }
}