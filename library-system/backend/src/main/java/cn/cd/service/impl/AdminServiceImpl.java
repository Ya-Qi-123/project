package cn.cd.service.impl;

import cn.cd.domain.TAdmin;
import cn.cd.mapper.AdminMapper;
import cn.cd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public TAdmin selectByEmailAndPassword(String email, String password) {
        TAdmin Admin = adminMapper.selectByEmailAndPassword(email, password);
        return Admin;
    }
}