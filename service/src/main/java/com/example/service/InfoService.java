package com.example.service;

import com.example.dto.Info;

public interface InfoService {
    Info save(Info entity);

    Info findOne(Long id);

    boolean delete(Long id);
}
