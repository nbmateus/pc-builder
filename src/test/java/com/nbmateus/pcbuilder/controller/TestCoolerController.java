package com.nbmateus.pcbuilder.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
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

                System.out.println("\ntestGetCoolerByIdNotFound: " + mvcResult.getResponse().getStatus() + "\n");
                System.out.println(
                                "\ntestGetCoolerByIdNotFound: " + mvcResult.getResponse().getContentAsString() + "\n");
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testAddCoolerSuccessfully() throws Exception {
                Cooler cooler = new Cooler("coolerMaker", "coolerName1", 1, 2, 1, 1, 1);

                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.post("/cooler/add").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(objectMapper.writeValueAsString(cooler)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CREATED.value());

        }

        @Test
        public void testAddCoolerDuplicated() throws Exception {
                Cooler cooler = new Cooler("coolerMaker1", "coolerName2", 2, 2, 2, 2, 2);

                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.post("/cooler/add").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(objectMapper.writeValueAsString(cooler)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateCoolerSuccesfully() throws Exception {

                Cooler coolerId4 = new Cooler("coolerMaker2", "coolerName4", 2, 1, 3, 2, 2);
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/cooler/4").contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(coolerId4)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.OK.value());
        }

        @Test
        public void testUpdateCoolerDuplicated() throws Exception {

                Cooler coolerId4 = new Cooler("coolerMaker1", "coolerName1", 2, 2, 2, 2, 2);
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/cooler/4").contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(coolerId4)))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.CONFLICT.value());
        }

        @Test
        public void testUpdateCoolerNotFound() throws Exception {
                MvcResult mvcResult = mockMvc.perform(
                                MockMvcRequestBuilders.get("/cooler/8").accept(MediaType.APPLICATION_JSON_VALUE))
                                .andReturn();

                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void testDeleteCoolerSuccessfully() throws Exception {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cooler/1")).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
        }

        @Test
        public void testDeleteCoolerNotFound() throws Exception {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cooler/10")).andReturn();
                assertTrue(mvcResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value());
        }
}