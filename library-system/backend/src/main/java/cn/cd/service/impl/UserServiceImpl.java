package cn.cd.service.impl;

import cn.cd.service.LendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cd.domain.TUser;
import cn.cd.service.UserService;
import cn.cd.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2025-06-21 09:03:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, TUser>
        implements UserService {
    @Autowired
    private UserMapper tUserMapper;

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
    public int registerService(String username, String password,
                                 String email, String phone) {
        return tUserMapper.register(username, password, email, phone);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return tUserMapper.updateStatus(id, status);
    }


    @Override
    public int delete(Long id) {
        return 0;
    }


}




