package com.example.web.unit;

import com.example.dto.Info;
import com.example.service.InfoService;
import com.example.web.controller.InfoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InfoControllerTest {
    private static final Long INFO_ID_1234 = 1234L;
    private static final String INFO_TEST = "Info 1234 New";
    private static final LocalDateTime INFO_CREATION_DATE_TIME = LocalDateTime.of(2011, 12, 9, 19, 15, 20);

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    InfoController infoController;

    @MockBean
    InfoService infoService;

    //data sample
    Info info;

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.infoController).build();// Standalone context
        info = new Info(1234L, "Info 1234 New", "2015-10-25T19:13:21");
    }

    @Test
    public void testGetInfo() throws Exception {
        // Mocking service
        when(infoService.findOne(any(Long.class))).thenReturn(info);

        mockMvc.perform(get("/api/info/1234").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1234)))
                .andExpect(jsonPath("$.info", is(INFO_TEST)))
        ;
    }

    @Test
    public void postInfo_ShouldReturnCreatedStatusAndCorrectInfo() throws Exception {
        String jsonExpected = "{\"id\":1234,\"info\":\"Info 1234 New\",\"creationDateTime\":\"2015-10-25T19:13:21\"}";

        // Mocking service
        when(infoService.save(any(Info.class))).thenReturn(info);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/info/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"info\":\"Info 1234 New\",\"creationDateTime\":\"2015-10-25T19:13:21\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(jsonExpected));
        ;
    }

    @Test
    public void deleteInfo_ShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/info/{id}", INFO_ID_1234))
                .andExpect(status().isNoContent());
    }
}
