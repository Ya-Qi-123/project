package cn.cd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cd.domain.TUser;
import cn.cd.service.TUserService;
import cn.cd.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2025-06-21 09:03:06
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
        implements TUserService{
    @Autowired
    private TUserMapper tUserMapper;

    // TUserServiceImpl 中新增的邮箱登录方法
    @Override
    public TUser loginServiceByEmailAndPassword(String email, String password) {
        TUser tUser = tUserMapper.getByEmailAndPassword(email, password); // Mapper 层查询邮箱+密码匹配的用户
        return tUser;
    }

    @Override
    public TUser loginServiceByPhoneAndPassword(String phone, String password) {
        TUser tUser = tUserMapper.getByPhoneAndPassword(phone, password);
        return tUser;
    }

    @Override
    public TUser registerServiceByEmailAndPassword(TUser tUser) {
        return null;
    }

    @Override
    public TUser registerServiceByPhoneAndPassword(TUser tUser) {
        return null;
    }


}




