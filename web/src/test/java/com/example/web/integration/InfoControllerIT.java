package com.example.web.integration;

import com.example.web.controller.InfoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InfoControllerIT {
    private static final Long INFO_ID_1234 = 1234l;

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    InfoController infoController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
    }

    @Test
    public void getInfo_ShouldReturnCorrectInfo() throws Exception {
        String jsonExpected = "{\"id\":1234,\"info\":\"Info 1234\",\"creationDateTime\":\"2001-12-12T13:40:30\"}";

        mockMvc.perform(get("/api/info/{id}", INFO_ID_1234))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(jsonExpected));
    }

    @Test
    public void postInfo_ShouldReturnCreatedStatusAndCorrectInfo() throws Exception {
        String jsonExpected = "{\"id\":1234,\"info\":\"Info 1234 New\",\"creationDateTime\":\"2015-10-25T19:13:21\"}";

        mockMvc.perform(post("/api/info/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"info\":\"Info 1234 New\",\"creationDateTime\":\"2015-10-25T19:13:21\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(jsonExpected));
        ;
    }

    @Test
    public void deleteInfo_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/info/{id}", INFO_ID_1234))
                .andExpect(status().isNoContent());
    }
}
