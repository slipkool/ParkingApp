package com.example.dao.imp;

import com.example.dao.IInfoDao;
import com.example.entity.Info;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class IInfoDaoImpl implements IInfoDao {
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
