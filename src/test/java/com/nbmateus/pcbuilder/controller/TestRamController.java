package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.dto.MotherboardDto;
import com.nbmateus.pcbuilder.model.RAM;
import com.nbmateus.pcbuilder.model.RamType;

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
@Sql("/ramData.sql")
@Sql("/userData.sql")
@AutoConfigureMockMvc
public class TestRamController {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testGetAll() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/ram/all").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                RAM[] ramList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RAM[].class);
                assertTrue(ramList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testGetRamByIdSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/ram/3").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                RAM ramId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RAM.class);

                assertTrue(ramId3.getId() == 3);
        }

        @Test
        public void testGetRamByIdNotFound() throws Exception {
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.get("/ram/9").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddRamSuccessfully() throws Exception {
                RAM ram = new RAM("ramMaker2", "ramName5", 1, 1, RamType.DDR4, 4, 3200, "cl16");

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add ram
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/ram/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(ram))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddRamDuplicated() throws Exception {
                RAM ram = new RAM("ramMaker2", "ramName4", 1, 1, RamType.DDR4, 4, 3200, "cl16");

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // add ram
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/ram/add")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(ram))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateRamSuccesfully() throws Exception {

                RAM ramId4 = new RAM("ramMaker2", "ramName4", 1, 1, RamType.DDR4, 4, 3200, "cl16");

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update ram
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/ram/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ramId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdateRamDuplicated() throws Exception {

                RAM ramId4 = new RAM("ramMaker1", "ramName1", 1, 1, RamType.DDR4, 4, 3200, "cl16");

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update ram
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/ram/4")
                                .header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ramId4))).andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateRamNotFound() throws Exception {
                RAM ram = new RAM("rammaker", "ramname", 1, 1, RamType.DDR4, 4, 3200, "cl16");

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // update ram
                mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/ram/8").header("Authorization",
                                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                                MessageResponse.class).getMessage())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(ram)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeleteRamSuccessfully() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete ram
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/ram/1").header("Authorization", objectMapper
                                .readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class)
                                .getMessage())).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeleteRamNotFound() throws Exception {

                // login as admin
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(new LoginForm("admin", "adminpass1"))))
                                .andReturn();

                // delete ram
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/ram/10").header("Authorization",
                                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                MessageResponse.class).getMessage()))
                                .andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testGetRamListByMoboSpecs() throws Exception {
                MotherboardDto motherboardDto = new MotherboardDto(RamType.DDR4, 2666);

                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ram/filter-by-motherboard-specs")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(motherboardDto))).andReturn();

                RAM[] ramList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RAM[].class);

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value() && ramList.length == 2);
                for (RAM ram : ramList) {
                        assertTrue(ram.getSpeed() <= 2666 && ram.getType() == RamType.DDR4);
                }

        }
}