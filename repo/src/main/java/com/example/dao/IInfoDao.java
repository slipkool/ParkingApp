package com.example.dao;

import com.example.entity.Info;

public interface IInfoDao {
    Info save(Info entity);

    Info findOne(Long id);

    boolean delete(Long id);
}
