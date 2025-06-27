package cn.cd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cd.domain.TUser;
import cn.cd.service.UserService;
import cn.cd.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        TUser tUser = tUserMapper.getByPhoneAndPassword(phone, password); // Mapper 层查询邮箱+密码匹配的用户
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
        int temp = tUserMapper.delete(id);
        return temp;
    }

    @Override
    public int updatePersonalInformation(Long id, String username, String email,
                                         String phone, String gender) {
        return tUserMapper.updatePersonalInformation(id, username, email, phone, gender);
    }

    @Override
    public TUser getByEmail(String email) {
        return tUserMapper.getByEmail(email);
    }

    @Override
    public TUser getByPhone(String phone) {
        return tUserMapper.getByPhone(phone);
    }

    @Override
    public TUser getByUsername(String username) {
        return tUserMapper.getByUsername(username);
    }

}




