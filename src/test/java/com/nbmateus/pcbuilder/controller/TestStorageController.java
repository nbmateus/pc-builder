package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbmateus.pcbuilder.model.Storage;
import com.nbmateus.pcbuilder.model.StorageType;

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
@Sql("/storageData.sql")
@AutoConfigureMockMvc
public class TestStorageController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/storage/all").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        Storage[] storageList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Storage[].class);
        assertTrue(storageList.length > 0 && mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void testGetStorageByIdSuccessfully() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/storage/3").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        Storage storageId3 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Storage.class);

        assertTrue(storageId3.getId() == 3);
    }

    @Test
    public void testGetStorageByIdNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/storage/9").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        System.out.println("\ntestGetStorageByIdNotFound: "+mvcResult.getResponse().getStatus()+"\n");
        System.out.println("\ntestGetStorageByIdNotFound: "+mvcResult.getResponse().getContentAsString()+"\n");
        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testAddStorageSuccessfully() throws Exception {
        Storage storage = new Storage("storageMaker2", "storageName5", 1, 1, StorageType.M2, 500, 300, "1TB");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/storage/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(storage)))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

    }

    @Test
    public void testAddStorageDuplicated() throws Exception {
        Storage storage = new Storage("storageMaker1", "storageName1", 1, 1, StorageType.M2, 500, 300, "1TB");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/storage/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(storage)))
                .andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
    }

    @Test
    public void testUpdateStorageSuccesfully() throws Exception {

        Storage storageId4 = new Storage("storageMaker2", "storageName4", 1, 1, StorageType.M2, 500, 300, "1TB");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/storage/4").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageId4))).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
    }

    @Test
    public void testUpdateStorageDuplicated() throws Exception {

        Storage storageId4 = new Storage("storageMaker2", "storageName3", 1, 1, StorageType.M2, 500, 300, "1TB");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/storage/4").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageId4))).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
    }

    @Test
    public void testUpdateStorageNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/storage/8").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDeleteStorageSuccessfully() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/storage/1")).andReturn();
            assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testDeleteStorageNotFound() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/storage/10")).andReturn();
            assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
    }
}