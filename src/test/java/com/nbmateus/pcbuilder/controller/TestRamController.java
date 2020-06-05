package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@AutoConfigureMockMvc
public class TestRamController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/ram/all").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        RAM[] ramList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RAM[].class);
        assertTrue(ramList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void testGetRamByIdSuccessfully() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/ram/3").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        RAM ramId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RAM.class);

        assertTrue(ramId3.getId() == 3);
    }

    @Test
    public void testGetRamByIdNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/ram/9").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        System.out.println("\ntestGetRamByIdNotFound: "+mvcResult.getResponse().getStatus()+"\n");
        System.out.println("\ntestGetRamByIdNotFound: "+mvcResult.getResponse().getContentAsString()+"\n");
        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testAddRamSuccessfully() throws Exception {
        RAM ram = new RAM("ramMaker2", "ramName5", 1, 1, RamType.DDR4, 4, 2666, "cl16");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/ram/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ram)))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

    }

    @Test
    public void testAddRamDuplicated() throws Exception {
        RAM ram = new RAM("ramMaker2", "ramName4", 1, 1, RamType.DDR4, 4, 2666, "cl16");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/ram/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ram)))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
    }

    @Test
    public void testUpdateRamSuccesfully() throws Exception {

        RAM ramId4 = new RAM("ramMaker2", "ramName4", 1, 1, RamType.DDR4, 4, 2666, "cl16");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/ram/4").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ramId4))).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void testUpdateRamDuplicated() throws Exception {

        RAM ramId4 = new RAM("ramMaker1", "ramName1", 1, 1, RamType.DDR4, 4, 2666, "cl16");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/ram/4").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ramId4))).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
    }

    @Test
    public void testUpdateRamNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/ram/8").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDeleteRamSuccessfully() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/ram/1")).andReturn();
            assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testDeleteRamNotFound() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/ram/10")).andReturn();
            assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }
}