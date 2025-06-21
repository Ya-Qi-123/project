package cn.cd.service.impl;

import cn.cd.domain.TAdmin;
import cn.cd.mapper.TAdminMapper;
import cn.cd.service.TAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAdminServiceImpl implements TAdminService {

    @Autowired
    private TAdminMapper adminMapper;


    public TAdmin login(String adminname, String password) {
        return adminMapper.selectByAdminnameAndPassword(adminname, password);
    }

    @Override
    public TAdmin getByAdminnameAndPassword(String adminname, String password) {
        TAdmin tAdmin = adminMapper.selectByAdminnameAndPassword(adminname, password);
        return tAdmin;
    }
}