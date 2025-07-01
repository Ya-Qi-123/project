package cn.cd.service.impl;

import cn.cd.domain.TAdmin;
import cn.cd.mapper.AdminMapper;
import cn.cd.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, TAdmin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public TAdmin selectByEmailAndPassword(String email, String password) {
        QueryWrapper<TAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("password", password);
        return adminMapper.selectOne(queryWrapper);
//        return adminMapper.selectByEmailAndPassword(email, password);
    }
}