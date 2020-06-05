package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.model.MotherboardSize;
import com.nbmateus.pcbuilder.model.PcCase;

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
@Sql("/pc_caseData.sql")
@AutoConfigureMockMvc
public class TestPcCaseController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/pcCase/all").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        PcCase[] pcCaseList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PcCase[].class);
        assertTrue(pcCaseList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void testGetPcCaseByIdSuccessfully() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/pcCase/3").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        PcCase pcCaseId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PcCase.class);

        assertTrue(pcCaseId3.getId() == 3);
    }

    @Test
    public void testGetPcCaseByIdNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/pcCase/9").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        System.out.println("\ntestGetPcCaseByIdNotFound: "+mvcResult.getResponse().getStatus()+"\n");
        System.out.println("\ntestGetPcCaseByIdNotFound: "+mvcResult.getResponse().getContentAsString()+"\n");
        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testAddPcCaseSuccessfully() throws Exception {
        PcCase pcCase = new PcCase("pcCaseMaker2", "pcCaseName5", 1, 1, MotherboardSize.ATX, 1, 1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/pcCase/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(pcCase)))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

    }

    @Test
    public void testAddPcCaseDuplicated() throws Exception {
        PcCase pcCase = new PcCase("pcCaseMaker1", "pcCaseName1", 1, 1, MotherboardSize.ATX, 1, 1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/pcCase/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(pcCase)))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
    }

    @Test
    public void testUpdatePcCaseSuccesfully() throws Exception {

        PcCase pcCaseId4 = new PcCase("pcCaseMaker2", "pcCaseName4", 1, 1, MotherboardSize.ITX, 1, 1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/pcCase/4").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pcCaseId4))).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void testUpdatePcCaseDuplicated() throws Exception {

        PcCase pcCaseId4 = new PcCase("pcCaseMaker1", "pcCaseName2", 1, 1, MotherboardSize.ITX, 1, 1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/pcCase/4").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pcCaseId4))).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
    }

    @Test
    public void testUpdatePcCaseNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/pcCase/8").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDeletePcCaseSuccessfully() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/pcCase/1")).andReturn();
            assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testDeletePcCaseNotFound() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/pcCase/10")).andReturn();
            assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }
}