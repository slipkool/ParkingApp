package com.example.service;

import com.example.dto.Info;
import com.example.service.imp.InfoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfoServiceImpl.class)
public class InfoServiceTest {
    private static final Long INFO_ID_1234 = 1234L;
    private static final String INFO_TEST = "Test 1234";
    private static final LocalDateTime INFO_CREATION_DATE_TIME = LocalDateTime.of(2011, 12, 9, 19, 15, 20);

    @Autowired
    InfoService infoService;

    @Test
    public void testGetInfo() {
        Info info = infoService.findOne(INFO_ID_1234);
        assertThat(info).isNotNull();
    }

    @Test
    public void testCreateInfo() {
        Info infoToSave = createInfo(null, INFO_TEST, INFO_CREATION_DATE_TIME);
        Info infoSaved = infoService.save(infoToSave);
        assertThat(infoSaved.getId()).isNotNull();
    }

    private Info createInfo(Long id, String info, LocalDateTime creationDateTime) {
        return new Info()
                .setId(id)
                .setInfo(info)
                .setCreationDateTime("Info 1234 New");
    }

    @Test
    public void testDeleteInfo(){
        boolean answer = infoService.delete(INFO_ID_1234);
        assertThat(answer).isTrue();
    }

    @Test
    public void testDeleteInfoFalse(){
        boolean answer = infoService.delete(0L);
        assertThat(answer).isFalse();
    }
}
