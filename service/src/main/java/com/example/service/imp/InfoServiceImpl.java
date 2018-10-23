package com.example.service.imp;

import com.example.dto.Info;
import com.example.service.InfoService;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Info save(Info info) {
        return info.setId(1234L);
    }

    @Override
    public Info findOne(Long id) {
        return new Info(id, "Info 1234", "2001-12-12T13:40:30");
    }

    @Override
    public boolean delete(Long id) {
        boolean answer = false;
        if (id != 0)
            answer = true;
        return answer;
    }
}
