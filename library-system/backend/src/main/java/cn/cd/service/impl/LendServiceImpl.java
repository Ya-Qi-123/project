package cn.cd.service.impl;

import cn.cd.domain.TLendrecord;
import cn.cd.mapper.LendrecordMapper;
import cn.cd.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LendServiceImpl implements LendService {
    @Autowired
    private LendrecordMapper lendrecordMapper;
    @Override
    public TLendrecord countByCategory() {
        TLendrecord lendrecord = lendrecordMapper.countByCategory();
        return lendrecord;
    }
}
